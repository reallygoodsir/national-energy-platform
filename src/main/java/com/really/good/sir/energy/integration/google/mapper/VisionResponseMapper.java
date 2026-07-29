package com.really.good.sir.energy.integration.google.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.really.good.sir.energy.integration.google.dto.response.DetectedTextBlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VisionResponseMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(VisionResponseMapper.class);

    public List<DetectedTextBlock> parse(final JsonNode response) {

        final List<DetectedTextBlock> blocks = new ArrayList<>();

        final JsonNode annotations = response
                .path("responses").path(0)
                .path("textAnnotations");

        if (!annotations.isArray() || annotations.isEmpty()) {
            LOGGER.warn("No text annotations found in Vision API response");
        } else {

            for (int i = 1; i < annotations.size(); i++) {
                final JsonNode annotation = annotations.get(i);
                final String text = annotation.path("description").asText();
                final double area = computeBoundingBoxArea(annotation.path("boundingPoly").path("vertices"));
                blocks.add(new DetectedTextBlock(text, area));
            }
        }

        return blocks;
    }

    private double computeBoundingBoxArea(final JsonNode vertices) {

        double area = 0;

        if (vertices.isArray() && vertices.size() >= 4) {

            int minX = vertices.get(0).path("x").asInt();
            int maxX = minX;
            int minY = vertices.get(0).path("y").asInt();
            int maxY = minY;

            for (final JsonNode vertex : vertices) {
                final int x = vertex.path("x").asInt();
                final int y = vertex.path("y").asInt();

                minX = Math.min(minX, x);
                maxX = Math.max(maxX, x);
                minY = Math.min(minY, y);
                maxY = Math.max(maxY, y);
            }

            area = (double) (maxX - minX) * (maxY - minY);
        }

        return area;
    }
}