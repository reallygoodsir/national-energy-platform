package com.really.good.sir.energy.integration.google.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.really.good.sir.energy.integration.google.dto.request.VisionAnnotateRequestBody;
import com.really.good.sir.energy.integration.google.dto.response.DetectedTextBlock;
import com.really.good.sir.energy.integration.google.mapper.VisionResponseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class VisionOcrClient {

    private static final Logger log = LoggerFactory.getLogger(VisionOcrClient.class);

    private final RestTemplate restTemplate;
    private final VisionResponseMapper responseMapper;
    private final String apiKey;
    private final String endpoint;

    public VisionOcrClient(
            RestTemplate restTemplate,
            VisionResponseMapper responseMapper,
            @Value("${google.vision.api-key}") String apiKey,
            @Value("${google.vision.endpoint}") String endpoint
    ) {
        this.restTemplate = restTemplate;
        this.responseMapper = responseMapper;
        this.apiKey = apiKey;
        this.endpoint = endpoint;
    }

    public List<DetectedTextBlock> detectText(byte[] imageBytes) {

        log.info("Sending image to Vision API, size={} bytes", imageBytes.length);

        VisionAnnotateRequestBody requestBody = VisionAnnotateRequestBody.forTextDetection(imageBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<VisionAnnotateRequestBody> httpEntity = new HttpEntity<>(requestBody, headers);

        String url = endpoint + "?key=" + apiKey;

        JsonNode response = restTemplate.postForObject(url, httpEntity, JsonNode.class);

        List<DetectedTextBlock> blocks = responseMapper.parse(response);

        log.info("Vision API returned {} text block(s)", blocks.size());

        return blocks;
    }
}