package com.fashion_store.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {
    INTERNAL_EXCEPTION(99, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_EXIST(11, "Not exist", HttpStatus.NOT_FOUND),
    EXISTED(12, "Exited", HttpStatus.BAD_REQUEST),
    INVALID_KEY(13, "Invalid key", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_NAME(14, "Invalid name", HttpStatus.BAD_REQUEST),
    INVALID_EMAIL(15, "Invalid email", HttpStatus.BAD_REQUEST),
    INVALID_PHONE(16, "Invalid phone", HttpStatus.BAD_REQUEST);


    int code;
    String message;
    HttpStatusCode httpStatusCode;
}