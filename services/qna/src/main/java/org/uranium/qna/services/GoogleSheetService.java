package org.uranium.qna.services;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.SheetsScopes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.google.api.services.sheets.v4.Sheets;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import com.google.api.services.sheets.v4.model.ValueRange;
import org.uranium.qna.beans.SpreadsheetQA;
import org.uranium.qna.processors.Processor;

@Service
public class GoogleSheetService {
    private Sheets sheetsService;
    private Processor processor;
    @Value("${sources.google.name}")
    private String applicationName;
    @Value("${sources.google.credential}")
    private String credentialPath;

    public void setProcessor(Processor processor) throws IOException {
        this.processor = processor;
        initSheetsService();
    }

    private GoogleCredential googleCredential() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(credentialPath);
        return GoogleCredential.fromStream(inputStream)
                .createScoped(Collections.singletonList(SheetsScopes.SPREADSHEETS)); // Adjust scopes as needed
    }

    private void initSheetsService() throws IOException {
        HttpTransport httpTransport = new NetHttpTransport();
        JacksonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        sheetsService = new Sheets.Builder(httpTransport, jsonFactory, googleCredential())
                .setApplicationName(applicationName)
                .build();
    }

    @Async
    public void processSpreadsheet(SpreadsheetQA spec) throws IOException {
        String sheetName = spec.getSheetName();
        int start = spec.getStartColumnIndex();
        int end = spec.getEndColumnIndex();
        char questionColumnLabel = spec.getQuestionColumn();

        System.out.println("Gathering questions from spreadsheet..");
        StringBuilder rangeQueryBuilder = new StringBuilder().append(questionColumnLabel)
                .append(start).append(":")
                .append(questionColumnLabel)
                .append(end);
        String range = rangeQueryBuilder.toString();
        ValueRange response = sheetsService.spreadsheets().values()
                .get(getSpreadsheetIdFromUrl(spec.getUrl()), sheetName + "!" + range) // Get data from data column
                .execute();
        List<List<Object>> values = response.getValues();

        int processingRow = start;

        System.out.println("Questions collected, now processing answers");
        for (List<Object> row : values) {
            String value = (String) row.get(0);
            String apiResponse = processor.process(value + spec.getSuffix());
            updateSheet(getSpreadsheetIdFromUrl(spec.getUrl()), sheetName, spec.getAnswerColumn(),
                    processingRow, apiResponse);
            if (processingRow % 10 == 0) {
                System.out.println("Processed up to : " + processingRow);
            }
            processingRow++;
        }
        System.out.println("Processing completed, please check the spreadsheet");
    }

    // Function to extract spreadsheet ID from URL
    private String getSpreadsheetIdFromUrl(String url) {
        return url.split("/")[5];
    }

    private void updateSheet(String spreadsheetId, String sheetName, char column,
                             int rowNumber, Object value) throws IOException {
        ValueRange body = new ValueRange()
                .setValues(Arrays.asList(Arrays.asList(value)));
        StringBuilder updateRowBuilder = new StringBuilder()
                .append(sheetName)
                .append("!")
                .append(column)
                .append(rowNumber);
        sheetsService.spreadsheets().values()
                .update(spreadsheetId, updateRowBuilder.toString(), body)
                .setValueInputOption("USER_ENTERED")
                .execute();
    }
}

