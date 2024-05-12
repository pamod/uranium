package org.uranium.qna.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uranium.qna.beans.SpreadsheetQA;
import org.uranium.qna.processors.GeminiProcessor;
import org.uranium.qna.services.GoogleSheetService;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/qna")
public class QController {
    private final GoogleSheetService service;

    public QController(GoogleSheetService service, GeminiProcessor processor) throws IOException {
        this.service = service;
        this.service.setProcessor(processor);
    }

    @PostMapping("/sheet")
    public ResponseEntity<String> fill(SpreadsheetQA spec) throws IOException {
        // Start asynchronous processing in the background
        CompletableFuture.runAsync(() -> {
                    try {
                        service.processSpreadsheet(spec);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                },
                // Optional: Provide a custom Executor for finer control
                Executors.newSingleThreadExecutor());
        return ResponseEntity.ok("spreadsheet accepted for processing");
    }
}
