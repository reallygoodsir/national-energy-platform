package com.really.good.sir.energy.dto.response;

public class MeterScanResponse {

    private String detectedValue;

    public MeterScanResponse(String detectedValue) {
        this.detectedValue = detectedValue;
    }

    public String getDetectedValue() {
        return detectedValue;
    }
}