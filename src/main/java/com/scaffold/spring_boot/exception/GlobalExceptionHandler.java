package com.scaffold.spring_boot.exception;

import java.time.LocalDate;

import jakarta.validation.ConstraintViolation;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.scaffold.spring_boot.dto.response.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    // Exception
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handlingException(RuntimeException e) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
        // bad request
        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        return ResponseEntity.badRequest().body(apiResponse);
    }
    // Runtime Exception
    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException e) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage(e.getMessage());
        // bad request
        apiResponse.setCode(400);
        return ResponseEntity.badRequest().body(apiResponse);
    }

    // MethodArgumentNotValidException
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handlingValidationException(MethodArgumentNotValidException e) {
        String enumKey = e.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        try {
            errorCode = ErrorCode.valueOf(enumKey);
            var constraintViolation = e.getBindingResult().getAllErrors().stream()
                    .filter(error -> {
                        error.unwrap(ConstraintViolation.class);
                        return true;
                    })
                    .findFirst()
                    .map(error -> error.unwrap(ConstraintViolation.class))
                    .orElseThrow(() -> new RuntimeException("Constraint violation not found"));

            var atributes = constraintViolation.getConstraintDescriptor().getAttributes();

        } catch (IllegalArgumentException exception) {
            System.out.println(enumKey);
            // catch for the situation
        }
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage(errorCode.getMessage());
        apiResponse.setCode(errorCode.getCode());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    // AppException
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException e) {
        ErrorCode errorCode = e.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage(errorCode.getMessage());
        // bad request
        apiResponse.setCode(errorCode.getCode());
        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }

    // AccessDenied Exception
    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> handlingAccessDeniedException(AccessDeniedException e) {
        ErrorCode errorCode = ErrorCode.ACCESS_DENIED;
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage(errorCode.getMessage());
        apiResponse.setCode(errorCode.getCode());
        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<String>> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex) {
        // Check if the error is specifically for `LocalDate` conversion
        ex.getRequiredType();
        if (ex.getRequiredType().equals(LocalDate.class)) {
            ApiResponse<String> response = new ApiResponse<>();
            response.setCode(400);
            response.setMessage("Invalid date format for parameter '" + ex.getName()
                    + "'. Expected format is 'YYYY-MM-DD'. Received value: '" + ex.getValue() + "'.");
            return ResponseEntity.badRequest().body(response);
        }

        // Generic response for other type mismatches
        ApiResponse<String> response = new ApiResponse<>();
        response.setCode(400);
        response.setMessage(
                "Invalid value for parameter '" + ex.getName() + "'. Expected type: " + ex.getRequiredType());
        return ResponseEntity.badRequest().body(response);
    }
}
