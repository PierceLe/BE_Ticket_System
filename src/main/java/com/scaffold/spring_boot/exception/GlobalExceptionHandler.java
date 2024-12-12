package com.scaffold.spring_boot.exception;


import com.scaffold.spring_boot.dto.request.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {
    //Exception
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handlingException(RuntimeException e) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
        // bad request
        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        return ResponseEntity.badRequest().body(apiResponse);
    }
    //Runtime Exception
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
        }
        catch (IllegalArgumentException exception) {
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
        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(apiResponse);
    }

    //AccessDenied Exception
    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> handlingAccessDeniedException(AccessDeniedException e) {
        ErrorCode errorCode = ErrorCode.ACCESS_DENIED;
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage(errorCode.getMessage());
        apiResponse.setCode(errorCode.getCode());
        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(apiResponse);
    }
}
