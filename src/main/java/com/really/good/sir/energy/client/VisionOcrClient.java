package com.really.good.sir.energy.client;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.List;

@Component
public class VisionOcrClient {

    private final RestTemplate restTemplate;
    private final VisionResponseParser responseParser;
    private final String apiKey;
    private final String endpoint;

    public VisionOcrClient(
            RestTemplate restTemplate,
            VisionResponseParser responseParser,
            @Value("${google.vision.api-key}") String apiKey,
            @Value("${google.vision.endpoint}") String endpoint
    ) {
        this.restTemplate = restTemplate;
        this.responseParser = responseParser;
        this.apiKey = apiKey;
        this.endpoint = endpoint;
    }

    public List<DetectedTextBlock> detectText(byte[] imageBytes) {

        System.out.println("[VisionOcrClient] Sending image to Vision API, size=" + imageBytes.length + " bytes");

        String base64Image = Base64.getEncoder().encodeToString(imageBytes);

        String requestBody = """
                {
                  "requests": [{
                    "image": { "content": "%s" },
                    "features": [{ "type": "TEXT_DETECTION" }]
                  }]
                }
                """.formatted(base64Image);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, headers);

        String url = endpoint + "?key=" + apiKey;

        JsonNode response = restTemplate.postForObject(url, httpEntity, JsonNode.class);

        List<DetectedTextBlock> blocks = responseParser.parse(response);

        System.out.println("[VisionOcrClient] Vision API returned " + blocks.size() + " text block(s)");

        return blocks;
    }
}