package com.really.good.sir.energy.integration.google.dto.request;

import java.util.List;

public final class VisionAnnotateRequestBody {

    private final List<VisionImageRequest> requests;

    private VisionAnnotateRequestBody(final List<VisionImageRequest> requests) {
        this.requests = List.copyOf(requests);
    }

    public static VisionAnnotateRequestBody forTextDetection(final byte[] imageBytes) {
        final VisionImage image = VisionImage.fromBytes(imageBytes);
        final VisionFeature feature = VisionFeature.textDetection();
        final VisionImageRequest request = new VisionImageRequest(image, List.of(feature));
        return new VisionAnnotateRequestBody(List.of(request));
    }

    public List<VisionImageRequest> getRequests() {
        return requests;
    }
}