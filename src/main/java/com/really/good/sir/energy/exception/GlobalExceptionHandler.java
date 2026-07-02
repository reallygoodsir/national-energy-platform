package com.really.good.sir.energy.exception;

import com.really.good.sir.energy.dto.response.ErrorResponse;
import com.really.good.sir.energy.dto.response.ValidationErrorResponse;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidation(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(err ->
                        errors.put(err.getField(), err.getDefaultMessage()));

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

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        ex.getMessage()
                ));
    }
}