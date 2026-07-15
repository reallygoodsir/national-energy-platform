package com.really.good.sir.energy.integration.google.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.really.good.sir.energy.integration.google.dto.request.VisionAnnotateRequestBody;
import com.really.good.sir.energy.integration.google.mapper.VisionResponseMapper;
import com.really.good.sir.energy.integration.google.dto.response.DetectedTextBlock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class VisionOcrClient {

    private final RestTemplate restTemplate;
    private final VisionResponseMapper responseParser;
    private final String apiKey;
    private final String endpoint;

    public VisionOcrClient(
            RestTemplate restTemplate,
            VisionResponseMapper responseParser,
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

        VisionAnnotateRequestBody requestBody = VisionAnnotateRequestBody.forTextDetection(imageBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<VisionAnnotateRequestBody> httpEntity = new HttpEntity<>(requestBody, headers);

        String url = endpoint + "?key=" + apiKey;

        JsonNode response = restTemplate.postForObject(url, httpEntity, JsonNode.class);

        List<DetectedTextBlock> blocks = responseParser.parse(response);

        System.out.println("[VisionOcrClient] Vision API returned " + blocks.size() + " text block(s)");

        return blocks;
    }
}