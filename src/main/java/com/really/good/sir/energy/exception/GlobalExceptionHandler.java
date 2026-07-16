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
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidation(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(err ->
                        errors.put(err.getField(), err.getDefaultMessage()));

        log.warn("Validation failed: {}", errors);

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
            EmailAlreadyExistsException ex) {

        log.warn("EmailAlreadyExistsException: {}", ex.getMessage());

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
            InvalidCredentialsException ex) {

        log.warn("InvalidCredentialsException: {}", ex.getMessage());

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
            PhoneNumberAlreadyExistsException ex) {

        log.warn("PhoneNumberAlreadyExistsException: {}", ex.getMessage());

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
            UserNotFoundException ex) {

        log.warn("UserNotFoundException: {}", ex.getMessage());

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
            RoleAlreadyAssignedException ex) {

        log.warn("RoleAlreadyAssignedException: {}", ex.getMessage());

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
            MeterTypeNotFoundException ex) {

        log.warn("MeterTypeNotFoundException: {}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.NOT_FOUND.value(),
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {

        log.error("Unhandled exception", ex);

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
            SerialNumberAlreadyExistsException ex) {

        log.warn("SerialNumberAlreadyExistsException: {}", ex.getMessage());

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
            ElectricMeterNotFoundException ex) {

        log.warn("ElectricMeterNotFoundException: {}", ex.getMessage());

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
            ApartmentNotFoundException ex) {

        log.warn("ApartmentNotFoundException: {}", ex.getMessage());

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
            ApartmentAccessDeniedException ex) {

        log.warn("ApartmentAccessDeniedException: {}", ex.getMessage());

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
            InvalidReadingValueException ex) {

        log.warn("InvalidReadingValueException: {}", ex.getMessage());

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
            MeterReadingNotDetectedException ex) {

        log.warn("MeterReadingNotDetectedException: {}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.UNPROCESSABLE_ENTITY.value(),
                        ex.getMessage()
                ));
    }
}