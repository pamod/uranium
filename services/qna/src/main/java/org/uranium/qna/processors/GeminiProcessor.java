package org.uranium.qna.processors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Represents the processor to call Gemini APIs for answers.
 */
@Component
public class GeminiProcessor implements Processor {
    @Value("${processors.gemini.url}")
    private String baseURL;
    @Value("${processors.gemini.key}")
    private String apiKey;
    @Value("${processors.gemini.delay}")
    private int delay;
    private RestTemplate restTemplate = new RestTemplate();

    private String extractResponseText(String jsonString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonString);
        // Accessing data using path methods
        JsonNode textNode = rootNode.path("candidates").get(0)
                .path("content")
                .path("parts").get(0)
                .path("text");
        String text = textNode.asText();
        return text;
    }

    private String sendPostRequest(String text) {
        // Replace with the actual URL
        String url = baseURL + "?key=" + apiKey;

        String payloadJson = "{\"contents\":[{\"parts\":[{\"text\":\"" + text + "\"}]}]}";

        // Send the POST request
        HttpEntity<String> entity = new HttpEntity<String>(payloadJson);
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.postForEntity(url, entity, String.class);
            return response.getBody();
        } catch (RestClientException e) {
            System.out.println("Error occurred while generating AI response " + e);
            return "{\"candidates\":[{\"content\":{\"parts\":[{\"text\":\"ERROR\"}]}}]}";
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String process(String question) {
        String response = null;
        try {
            response = extractResponseText(sendPostRequest(question));
        } catch (JsonProcessingException e) {
            System.out.println("Error occurred while processing answer to question " + question + " " + e);
            response = "ERROR";
        } finally {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted while sleep " + e);
            }
        }
        return response;
    }
}
