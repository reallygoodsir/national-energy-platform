package com.really.good.sir.energy.service;

import com.really.good.sir.energy.dto.response.MeterScanResponse;
import com.really.good.sir.energy.entity.ApartmentEntity;
import com.really.good.sir.energy.exception.ApartmentAccessDeniedException;
import com.really.good.sir.energy.exception.ApartmentNotFoundException;
import com.really.good.sir.energy.exception.MeterReadingNotDetectedException;
import com.really.good.sir.energy.integration.google.client.VisionOcrClient;
import com.really.good.sir.energy.integration.google.dto.response.DetectedTextBlock;
import com.really.good.sir.energy.repository.ApartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class MeterScanService {

    private static final Pattern DIGIT_PATTERN = Pattern.compile("^\\d+(\\.\\d+)?$");
    private static final Logger LOGGER = LoggerFactory.getLogger(MeterScanService.class);

    private final VisionOcrClient visionOcrClient;
    private final ApartmentRepository apartmentRepository;

    public MeterScanService(final VisionOcrClient visionOcrClient, final ApartmentRepository apartmentRepository) {
        this.visionOcrClient = visionOcrClient;
        this.apartmentRepository = apartmentRepository;
    }

    public MeterScanResponse scan(final Long apartmentId, final MultipartFile file, final String requesterEmail) {

        verifyOwnership(apartmentId, requesterEmail);

        final byte[] imageBytes;
        try {
            imageBytes = file.getBytes();
        } catch (IOException e) {
            throw new MeterReadingNotDetectedException(e);
        }

        final List<DetectedTextBlock> blocks = visionOcrClient.detectText(imageBytes);

        final List<DetectedTextBlock> candidates = blocks.stream()
                .filter(block -> DIGIT_PATTERN.matcher(block.getText()).matches())
                .toList();

        LOGGER.info("Candidate blocks after digit filter: {}", candidates);

        final String bestCandidate = candidates.stream()
                .max(Comparator
                        .comparingInt((DetectedTextBlock b) -> b.getText().replace(".", "").length())
                        .thenComparingDouble(DetectedTextBlock::getBoundingBoxArea))
                .map(DetectedTextBlock::getText)
                .orElseThrow(MeterReadingNotDetectedException::new);

        return new MeterScanResponse(bestCandidate);
    }

    private void verifyOwnership(final Long apartmentId, final String requesterEmail) {

        final ApartmentEntity apartment = apartmentRepository.findById(apartmentId)
                .orElseThrow(() -> new ApartmentNotFoundException(apartmentId));

        if (!apartment.getUser().getEmail().equals(requesterEmail)) {
            throw new ApartmentAccessDeniedException(apartmentId);
        }
    }
}