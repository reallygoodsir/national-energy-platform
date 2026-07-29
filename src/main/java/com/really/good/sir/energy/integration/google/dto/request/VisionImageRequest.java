package com.really.good.sir.energy.integration.google.dto.request;

import java.util.List;

public class VisionImageRequest {

    private final VisionImage image;
    private final List<VisionFeature> features;

    public VisionImageRequest(final VisionImage image, final List<VisionFeature> features) {
        this.image = image;
        this.features = features;
    }

    public VisionImage getImage() {
        return image;
    }

    public List<VisionFeature> getFeatures() {
        return features;
    }
}