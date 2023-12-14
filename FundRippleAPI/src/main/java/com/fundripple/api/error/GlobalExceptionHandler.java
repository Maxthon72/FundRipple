package com.fundripple.api.error;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;


    @RestControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler(CustomException.class)
        public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage(ex.getMessage());
            errorResponse.setErrorCode("CUSTOM_ERROR_CODE");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        // Inner class for error response
        @Getter
        static class ErrorResponse {
            @Setter
            private String message;

            @Setter
            @Getter
            private String errorCode;

            // Getters and Setters
        }
}
