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
    INVALID_VALUE(16, "Invalid value", HttpStatus.BAD_REQUEST),
    INVALID_SKU(17, "Sku must be at least 3 characters long", HttpStatus.BAD_REQUEST),
    INVALID_TYPE(18, "Invalid display type", HttpStatus.BAD_REQUEST),
    INVALID_PHONE(19, "Invalid phone", HttpStatus.BAD_REQUEST),
    INVALID_FILE(21, "Invalid file", HttpStatus.BAD_REQUEST),
    FILE_SAVE_FAILED(22, "Failed to save file", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_PRICE(23, "Invalid price", HttpStatus.BAD_REQUEST),
    INVALID_ORIGINAL_PRICE(30, "Original price must be greater than 0", HttpStatus.BAD_REQUEST),
    INVALID_SALE_PRICE(31, "Sale price must be greater than 0", HttpStatus.BAD_REQUEST),
    INVALID_PROMOTIONAL_PRICE(32, "Promotional price must be greater than 0", HttpStatus.BAD_REQUEST),
    INVALID_PROMOTION_TIME(33, "Invalid promotion time", HttpStatus.BAD_REQUEST),
    INVALID_VARIANT(33, "At least one variant is required", HttpStatus.BAD_REQUEST),
    INVALID_FORMAT_TIME(34, "Invalid format time", HttpStatus.BAD_REQUEST),
    INVALID_PROMOTION_START_TIME(34, "Invalid format time", HttpStatus.BAD_REQUEST),
    INVALID_PROMOTION_END_TIME(34, "Invalid format time", HttpStatus.BAD_REQUEST),
    INVALID_ATTRIBUTE_VALUE(34, "There must be at least 1 value attribute in the variable.", HttpStatus.BAD_REQUEST),
    VARIANT_ID_MISMATCH(35, "The variant ID does not match the list to update.", HttpStatus.BAD_REQUEST),
    INVALID_VARIANT_LIST(35, "Invalid variant data.", HttpStatus.BAD_REQUEST),
    INVALID_ATTRIBUTE_COUNT(20, "Attribute must have at least one value", HttpStatus.BAD_REQUEST);


    int code;
    String message;
    HttpStatusCode httpStatusCode;
}