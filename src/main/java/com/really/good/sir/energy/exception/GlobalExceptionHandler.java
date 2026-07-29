package com.really.good.sir.energy.exception;

import com.really.good.sir.energy.dto.response.ErrorResponse;
import com.really.good.sir.energy.dto.response.ValidationErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidation(
            final MethodArgumentNotValidException ex) {

        final Map<String, String> errors = new ConcurrentHashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(err ->
                        errors.put(err.getField(), err.getDefaultMessage()));

        LOGGER.warn("MethodArgumentNotValidException: {}, {}", errors, ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ValidationErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        errors
                ));
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmail(
            final EmailAlreadyExistsException ex) {

        LOGGER.warn("EmailAlreadyExistsException: {}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.CONFLICT.value(),
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleAuth(
            final InvalidCredentialsException ex) {

        LOGGER.warn("InvalidCredentialsException: {}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.UNAUTHORIZED.value(),
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(PhoneNumberAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handlePhone(
            final PhoneNumberAlreadyExistsException ex) {

        LOGGER.warn("PhoneNumberAlreadyExistsException: {}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.CONFLICT.value(),
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(
            final UserNotFoundException ex) {

        LOGGER.warn("UserNotFoundException: {}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.NOT_FOUND.value(),
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(RoleAlreadyAssignedException.class)
    public ResponseEntity<ErrorResponse> handleRoleAlreadyAssigned(
            final RoleAlreadyAssignedException ex) {

        LOGGER.warn("RoleAlreadyAssignedException: {}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.CONFLICT.value(),
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(MeterTypeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMeterTypeNotFound(
            final MeterTypeNotFoundException ex) {

        LOGGER.warn("MeterTypeNotFoundException: {}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.NOT_FOUND.value(),
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(final Exception ex) {

        LOGGER.error("Unhandled exception", ex);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Something went wrong"
                ));
    }

    @ExceptionHandler(SerialNumberAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleSerialNumberExists(
            final SerialNumberAlreadyExistsException ex) {

        LOGGER.warn("SerialNumberAlreadyExistsException: {}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(ElectricMeterNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleElectricMeterNotFound(
            final ElectricMeterNotFoundException ex) {

        LOGGER.warn("ElectricMeterNotFoundException: {}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.NOT_FOUND.value(),
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(ApartmentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleApartmentNotFound(
            final ApartmentNotFoundException ex) {

        LOGGER.warn("ApartmentNotFoundException: {}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.NOT_FOUND.value(),
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(ApartmentAccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleApartmentAccessDenied(
            final ApartmentAccessDeniedException ex) {

        LOGGER.warn("ApartmentAccessDeniedException: {}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.FORBIDDEN.value(),
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(InvalidReadingValueException.class)
    public ResponseEntity<ErrorResponse> handleInvalidMeterReading(
            final InvalidReadingValueException ex) {

        LOGGER.warn("InvalidReadingValueException: {}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(MeterReadingNotDetectedException.class)
    public ResponseEntity<ErrorResponse> handleReadingNotDetected(
            final MeterReadingNotDetectedException ex) {

        LOGGER.warn("MeterReadingNotDetectedException: {}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.UNPROCESSABLE_ENTITY.value(),
                        ex.getMessage()
                ));
    }
}