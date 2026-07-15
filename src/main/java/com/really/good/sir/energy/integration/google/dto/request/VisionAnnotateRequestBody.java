package com.really.good.sir.energy.integration.google.dto.request;

import java.util.List;

public class VisionAnnotateRequestBody {

    private final List<VisionImageRequest> requests;

    private VisionAnnotateRequestBody(List<VisionImageRequest> requests) {
        this.requests = requests;
    }

    public static VisionAnnotateRequestBody forTextDetection(byte[] imageBytes) {
        VisionImage image = VisionImage.fromBytes(imageBytes);
        VisionFeature feature = VisionFeature.textDetection();
        VisionImageRequest request = new VisionImageRequest(image, List.of(feature));
        return new VisionAnnotateRequestBody(List.of(request));
    }

    public List<VisionImageRequest> getRequests() {
        return requests;
    }
}