package com.scaffold.spring_boot.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum ErrorCode {
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
    USER_NAME_EXISTED(400, "Username already existed", HttpStatus.CONFLICT),
    UNIT_NAME_NOT_EXISTED(400, "Unit name is not existed", HttpStatus.NOT_FOUND),
    UNIT_NAME_EXISTED(400, "Unit name already existed", HttpStatus.CONFLICT),
    INVALID_TOKEN(401, "Invalid token format. Expected 'Bearer <token>'", HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED(403, "Access denied", HttpStatus.FORBIDDEN),
    UNIT_EXISTED(400, "Unit already existed", HttpStatus.BAD_REQUEST)
    ;
    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

}
