package com.scaffold.spring_boot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    PROJECT_NAME_EXISTED(400, "project name existed", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(400, "User is not exist", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTED(400, "User already existed", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EMAIL_EXISTED(400, "User email already existed", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(401, "Unauthorized", HttpStatus.UNAUTHORIZED),
    UNCATEGORIZED_EXCEPTION(406, "Uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_NAME_NOT_VALID(400, "Username is not valid", HttpStatus.BAD_REQUEST),
    USER_PASSWORD_NOT_VALID(401, "UserPassword is not valid", HttpStatus.BAD_REQUEST),
    INVALID_KEY(400, "Invalid message key", HttpStatus.BAD_REQUEST),
    AUTHENTICATION_FAILED(401, "Authentication failed", HttpStatus.UNAUTHORIZED),
    INVALID_JWT(401, "Invalid JWT", HttpStatus.UNAUTHORIZED),
    USERNAME_NOT_EMPTY(400, "Username is empty", HttpStatus.BAD_REQUEST),
    PASSWORD_NOT_EMPTY(400, "Password is empty", HttpStatus.BAD_REQUEST),
    EMAIL_NOT_EMPTY(400, "Email is empty", HttpStatus.BAD_REQUEST),
    UNIT_ID_NOT_EMPTY(400, "Unid id is empty", HttpStatus.BAD_REQUEST),
    ROLE_ID_NOT_EMPTY(400, "Role id is empty", HttpStatus.BAD_REQUEST),
    UNIT_ID_NOT_EXISTED(400, "unit id is not existed", HttpStatus.NOT_FOUND),
    PROJECT_ID_NOT_EXISTED(400, "project id is not existed", HttpStatus.BAD_REQUEST),
    PROJECT_NAME_NOT_EXISTED(400, "project name is not existed", HttpStatus.BAD_REQUEST),
    USER_NAME_EXISTED(400, "Username already existed", HttpStatus.CONFLICT),
    UNIT_NAME_NOT_EXISTED(400, "Unit name is not existed", HttpStatus.NOT_FOUND),
    UNIT_NAME_EXISTED(400, "Unit name already existed", HttpStatus.CONFLICT),
    INVALID_TOKEN(401, "Invalid token format. Expected 'Bearer <token>'", HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED(403, "Access denied", HttpStatus.FORBIDDEN),
    UNIT_EXISTED(400, "Unit already existed", HttpStatus.BAD_REQUEST),
    INVALID_DATETIME_FORMAT(400, "Invalid date format. Please use YYYY-MM-DD.", HttpStatus.BAD_REQUEST),
    USER_HAS_BEEN_LOCKED(400, "User has been locked", HttpStatus.CONFLICT),
    USER_STILL_ACTIVE(400, "User still active", HttpStatus.CONFLICT),
    UPLOAD_FILE_ERROR(400, "upload file error", HttpStatus.INTERNAL_SERVER_ERROR),
    FILE_EMPTY(400, "File is empty", HttpStatus.BAD_REQUEST),
    INVALID_FILE_TYPE(400, "Invalid file type", HttpStatus.BAD_REQUEST),
    DELETE_FILE_ERROR(400, "Delete file error", HttpStatus.BAD_REQUEST),
    AVATAR_ALREADY_DEFAULT(400, "Avatar already default", HttpStatus.CONFLICT),
    TOKEN_GENERATION_FAILED(400, "Token generation failed", HttpStatus.INTERNAL_SERVER_ERROR),
    ACCOUNT_LOCKED(400, "Account locked", HttpStatus.CONFLICT),
    PROJECT_ID_NOT_EMPTY(400, "Project id is not empty", HttpStatus.BAD_REQUEST),
    DESCRIPTION_NOT_EMPTY(400, "Description is empty", HttpStatus.BAD_REQUEST),
    EXPECTED_TIME_NOT_EMPTY(400, "Expected time is not empty", HttpStatus.BAD_REQUEST),
    ;
    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
