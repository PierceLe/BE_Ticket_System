package com.scaffold.spring_boot.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_EXISTED(400, "User already existed"),
    USER_EMAIL_EXISTED(400, "User email already existed"),
    UNAUTHORIZED(401, "Unauthorized"),
    UNCATEGORIZED_EXCEPTION(406, "Uncategorized exception"),
    USER_NAME_NOT_VALID(400, "Username is not valid"),
    USER_PASSWORD_NOT_VALID(400, "UserPassword is not valid"),
    INVALID_KEY(400, "Invalid message key"),
    AUTHENTICATION_FAILED(401, "Authentication failed"),
    INVALID_JWT(400, "Invalid JWT"),
    USERNAME_NOT_EMPTY(400, "Username is empty"),
    PASSWORD_NOT_EMPTY(400, "Password is empty"),
    EMAIL_NOT_EMPTY(400, "Email is empty"),
    UNIT_ID_NOT_EMPTY(400, "Unid id is empty"),
    ROLE_ID_NOT_EMPTY(400, "Role id is empty"),
    UNIT_ID_NOT_EXISTED(400, "unit id is not existed"),
    USER_NAME_EXISTED(400, "Username already existed"),
    UNIT_NAME_NOT_EXISTED(400, "Unit name is not existed"),
    ;
    private final int code;
    private final String message;

}
