package com.really.good.sir.energy.service;

import com.really.good.sir.energy.integration.google.client.VisionOcrClient;
import com.really.good.sir.energy.integration.google.dto.response.DetectedTextBlock;
import com.really.good.sir.energy.dto.response.MeterScanResponse;
import com.really.good.sir.energy.entity.ApartmentEntity;
import com.really.good.sir.energy.exception.ApartmentAccessDeniedException;
import com.really.good.sir.energy.exception.ApartmentNotFoundException;
import com.really.good.sir.energy.exception.MeterReadingNotDetectedException;
import com.really.good.sir.energy.repository.ApartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class MeterScanService {

    private static final Pattern DIGIT_PATTERN = Pattern.compile("^\\d+(\\.\\d+)?$");

    private final VisionOcrClient visionOcrClient;
    private final ApartmentRepository apartmentRepository;

    public MeterScanService(VisionOcrClient visionOcrClient, ApartmentRepository apartmentRepository) {
        this.visionOcrClient = visionOcrClient;
        this.apartmentRepository = apartmentRepository;
    }

    public MeterScanResponse scan(Long apartmentId, MultipartFile file, String requesterEmail) {

        verifyOwnership(apartmentId, requesterEmail);

        byte[] imageBytes;
        try {
            imageBytes = file.getBytes();
        } catch (IOException e) {
            throw new MeterReadingNotDetectedException();
        }

        List<DetectedTextBlock> blocks = visionOcrClient.detectText(imageBytes);

        List<DetectedTextBlock> candidates = blocks.stream()
                .filter(block -> DIGIT_PATTERN.matcher(block.getText()).matches())
                .toList();

        System.out.println("[MeterScanService] Candidate blocks after digit filter: " + candidates);

        String bestCandidate = candidates.stream()
                .max(Comparator
                        .comparingInt((DetectedTextBlock b) -> b.getText().replace(".", "").length())
                        .thenComparingDouble(DetectedTextBlock::getBoundingBoxArea))
                .map(DetectedTextBlock::getText)
                .orElseThrow(MeterReadingNotDetectedException::new);

        return new MeterScanResponse(bestCandidate);
    }

    private void verifyOwnership(Long apartmentId, String requesterEmail) {

        ApartmentEntity apartment = apartmentRepository.findById(apartmentId)
                .orElseThrow(() -> new ApartmentNotFoundException(apartmentId));

        if (!apartment.getUser().getEmail().equals(requesterEmail)) {
            throw new ApartmentAccessDeniedException(apartmentId);
        }
    }
}