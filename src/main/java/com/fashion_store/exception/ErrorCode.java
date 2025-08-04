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
    INVALID_FORM_FORMAT(34, "Invalid format value", HttpStatus.BAD_REQUEST),
    INVALID_PROMOTION_START_TIME(34, "Invalid format time", HttpStatus.BAD_REQUEST),
    INVALID_PROMOTION_END_TIME(34, "Invalid format time", HttpStatus.BAD_REQUEST),
    INVALID_ATTRIBUTE_VALUE(34, "There must be at least 1 value attribute in the variable.", HttpStatus.BAD_REQUEST),
    VARIANT_ID_MISMATCH(35, "The variant ID does not match the list to update.", HttpStatus.BAD_REQUEST),
    INVALID_VARIANT_LIST(36, "Invalid variant data.", HttpStatus.BAD_REQUEST),
    INVALID_CONTENT(37, "Content cannot be blank.", HttpStatus.BAD_REQUEST),
    INVALID_TITLE(38, "Invalid title", HttpStatus.BAD_REQUEST),
    INVALID_LIST_IMPORT_ITEM(39, "The import item list must have at least 1 item.", HttpStatus.BAD_REQUEST),
    INVALID_QUANTITY(40, "Quantity must be greater than 0.", HttpStatus.BAD_REQUEST),
    INVALID_VARIANT_ID(41, "Invalid variant id.", HttpStatus.BAD_REQUEST),
    INVALID_IMPORT_PRICE(42, "Invalid import price.", HttpStatus.BAD_REQUEST),
    INVALID_CODE(43, "Invalid code.", HttpStatus.BAD_REQUEST),
    INVALID_VOUCHER_TIME(44, "Invalid voucher time.", HttpStatus.BAD_REQUEST),
    INVALID_VOUCHER_DISCOUNT(45, "Invalid voucher discount.", HttpStatus.BAD_REQUEST),
    INVALID_ORDER_VALUE(46, "Invalid min order value.", HttpStatus.BAD_REQUEST),
    INVALID_VOUCHER_TYPE(47, "Invalid voucher type.", HttpStatus.BAD_REQUEST),
    INVALID_TYPE_DATA(48, "Invalid type data", HttpStatus.BAD_REQUEST),
    INVALID_ATTRIBUTE_COUNT(20, "Attribute must have at least one value", HttpStatus.BAD_REQUEST),
    INVALID_CUSTOMER_ID(49, "Invalid customer ID.", HttpStatus.BAD_REQUEST),
    INVALID_CUSTOMER_NAME(50, "Invalid customer name.", HttpStatus.BAD_REQUEST),
    INVALID_ADDRESS(51, "Invalid address.", HttpStatus.BAD_REQUEST),
    INVALID_ADDRESS_PHONE(52, "Invalid phone number in address.", HttpStatus.BAD_REQUEST),
    INVALID_CITY(53, "Invalid city.", HttpStatus.BAD_REQUEST),
    INVALID_DISTRICT(54, "Invalid district.", HttpStatus.BAD_REQUEST),
    INVALID_AUTH_PROVIDER(56, "Invalid auth provider.", HttpStatus.BAD_REQUEST),
    INVALID_WARD(55, "Invalid ward.", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(57, "Username must be at least 3 characters", HttpStatus.BAD_REQUEST),
    INVALID_DOB(58, "Date of birth be at least 17", HttpStatus.BAD_REQUEST),
    CANNOT_BE_DELETE(59, "Unable to delete administrator role", HttpStatus.BAD_REQUEST),
    CANNOT_BE_UPDATE(60, "Unable to update administrator role", HttpStatus.BAD_REQUEST),
    INVALID_ROLE_ID(61, "Invalid role id", HttpStatus.BAD_REQUEST),
    INVALID_LIST_PERMISSION_ID(62, "Invalid list permission id", HttpStatus.BAD_REQUEST);

    int code;
    String message;
    HttpStatusCode httpStatusCode;
}