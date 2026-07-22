package com.really.good.sir.energy.integration.google.dto.request;

public final class VisionFeature {

    private final String type;

    private VisionFeature(String type) {
        this.type = type;
    }

    public static VisionFeature textDetection() {
        return new VisionFeature("TEXT_DETECTION");
    }

    public String getType() {
        return type;
    }
}