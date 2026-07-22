package com.really.good.sir.energy.integration.google.dto.request;

import java.util.Base64;

public final class VisionImage {

    private final String content;

    private VisionImage(String content) {
        this.content = content;
    }

    public static VisionImage fromBytes(byte[] imageBytes) {
        String base64Content = Base64.getEncoder().encodeToString(imageBytes);
        return new VisionImage(base64Content);
    }

    public String getContent() {
        return content;
    }
}