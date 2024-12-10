package com.scaffold.spring_boot.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_EXISTED(400, "User already existed"),
    UNAUTHORIZED(401, "Unauthorized"),
    UNCATEGORIZED_EXCEPTION(406, "Uncategorized exception"),
    USER_NAME_NOT_VALID(400, "Username is not valid"),
    USER_PASSWORD_NOT_VALID(400, "UserPassword is not valid"),
    INVALID_KEY(400, "Invalid message key"),
    AUTHENTICATION_FAILED(401, "Authentication failed"),
    INVALID_JWT(400, "Invalid JWT"),
    ;
    private final int code;
    private final String message;

}
