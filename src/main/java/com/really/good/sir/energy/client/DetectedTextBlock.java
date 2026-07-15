package com.really.good.sir.energy.client;

public class DetectedTextBlock {

    private final String text;
    private final double boundingBoxArea;

    public DetectedTextBlock(String text, double boundingBoxArea) {
        this.text = text;
        this.boundingBoxArea = boundingBoxArea;
    }

    public String getText() {
        return text;
    }

    public double getBoundingBoxArea() {
        return boundingBoxArea;
    }

    @Override
    public String toString() {
        return text + " (area=" + boundingBoxArea + ")";
    }
}