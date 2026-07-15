package com.really.good.sir.energy.integration.google.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.really.good.sir.energy.integration.google.dto.response.DetectedTextBlock;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VisionResponseMapper {

    public List<DetectedTextBlock> parse(JsonNode response) {

        List<DetectedTextBlock> blocks = new ArrayList<>();

        JsonNode annotations = response
                .path("responses").path(0)
                .path("textAnnotations");

        if (!annotations.isArray() || annotations.isEmpty()) {
            System.out.println("[VisionResponseMapper] No text annotations found in response");
            return blocks;
        }

        for (int i = 1; i < annotations.size(); i++) {
            JsonNode annotation = annotations.get(i);
            String text = annotation.path("description").asText();
            double area = computeBoundingBoxArea(annotation.path("boundingPoly").path("vertices"));
            blocks.add(new DetectedTextBlock(text, area));
        }

        return blocks;
    }

    private double computeBoundingBoxArea(JsonNode vertices) {

        if (!vertices.isArray() || vertices.size() < 4) {
            return 0;
        }

        int minX = vertices.get(0).path("x").asInt();
        int maxX = minX;
        int minY = vertices.get(0).path("y").asInt();
        int maxY = minY;

        for (JsonNode vertex : vertices) {
            int x = vertex.path("x").asInt();
            int y = vertex.path("y").asInt();
            minX = Math.min(minX, x);
            maxX = Math.max(maxX, x);
            minY = Math.min(minY, y);
            maxY = Math.max(maxY, y);
        }

        return (double) (maxX - minX) * (maxY - minY);
    }
}