package com.really.good.sir.energy.dto.response;

public class MeterScanResponse {

    private final String detectedValue;

    public MeterScanResponse(final String detectedValue) {
        this.detectedValue = detectedValue;
    }

    public String getDetectedValue() {
        return detectedValue;
    }
}