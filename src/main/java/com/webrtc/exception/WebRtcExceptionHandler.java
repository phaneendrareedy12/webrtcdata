package com.webrtc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class WebRtcExceptionHandler {

    @ExceptionHandler(DeviceNotFoundException.class)
    public ResponseEntity<DeviceNotFoundErrMessage> resourceNotFoundException(DeviceNotFoundException ex, WebRequest request) {
        DeviceNotFoundErrMessage message = new DeviceNotFoundErrMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DeviceReqPayloadValErrMessage> handleValidationExceptions(
            MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        DeviceReqPayloadValErrMessage message = new DeviceReqPayloadValErrMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                errors,
                request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DeviceDetailsPresentException.class)
    public ResponseEntity<DeviceDetailsPresent> deviceDetailsPresent(DeviceDetailsPresentException ex, WebRequest request) {
        DeviceDetailsPresent message = new DeviceDetailsPresent(
                HttpStatus.ALREADY_REPORTED.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.ALREADY_REPORTED);
    }
}
