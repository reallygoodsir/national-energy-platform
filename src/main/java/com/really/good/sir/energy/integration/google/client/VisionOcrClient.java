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
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class VisionOcrClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(VisionOcrClient.class);

    private final RestTemplate restTemplate;
    private final VisionResponseMapper responseMapper;
    private final String apiKey;
    private final String endpoint;

    public VisionOcrClient(
            final RestTemplate restTemplate,
            final VisionResponseMapper responseMapper,
            @Value("${google.vision.api-key}") final String apiKey,
            @Value("${google.vision.endpoint}") final String endpoint
    ) {
        this.restTemplate = restTemplate;
        this.responseMapper = responseMapper;
        this.apiKey = apiKey;
        this.endpoint = endpoint;
    }

    public List<DetectedTextBlock> detectText(final byte[] imageBytes) {

        LOGGER.info("Sending image to Vision API, size={} bytes", imageBytes.length);

        final VisionAnnotateRequestBody requestBody = VisionAnnotateRequestBody.forTextDetection(imageBytes);

        final MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        final HttpEntity<VisionAnnotateRequestBody> httpEntity = new HttpEntity<>(requestBody, headers);

        final String url = endpoint + "?key=" + apiKey;

        final JsonNode response = restTemplate.postForObject(url, httpEntity, JsonNode.class);

        if (response == null) {
            throw new IllegalStateException("Vision API returned no response body.");
        }

        final List<DetectedTextBlock> blocks = responseMapper.parse(response);

        LOGGER.info("Vision API returned {} text block(s)", blocks.size());

        return blocks;
    }
}