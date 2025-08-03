package com.fashion_store.exception;

import com.fashion_store.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse<Void>> ExceptionHandler(Exception e) {
        ErrorCode errorCode = ErrorCode.INTERNAL_EXCEPTION;
        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(
                ApiResponse.<Void>builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidFormat(HttpMessageNotReadableException ex) {
        ErrorCode errorCode = ErrorCode.INVALID_FORMAT_TIME;
        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(
                ApiResponse.<Void>builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<Void>> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        ErrorCode errorCode = ErrorCode.INVALID_KEY;

        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            String fieldName = fieldError.getField();

            if ("typeMismatch".equals(fieldError.getCode())
                    || (fieldError.getCodes() != null && Arrays.stream(fieldError.getCodes()).anyMatch(code -> code.contains("typeMismatch")))) {
                if (fieldName.contains("promotionStartTime")) {
                    errorCode = ErrorCode.INVALID_PROMOTION_START_TIME;
                } else if (fieldName.contains("promotionEndTime")) {
                    errorCode = ErrorCode.INVALID_PROMOTION_END_TIME;
                } else if (fieldName.contains("variantList")) {
                    errorCode = ErrorCode.INVALID_VARIANT_LIST;
                } else if (fieldName.contains("image")) {
                    errorCode = ErrorCode.INVALID_FILE;
                } else {
                    errorCode = ErrorCode.INVALID_TYPE_DATA;
                }
            } else {
                try {
                    errorCode = ErrorCode.valueOf(fieldError.getDefaultMessage());
                } catch (IllegalArgumentException ex) {
                    log.error("ErrorCode {} does not exist in enum\n{}", fieldError.getDefaultMessage(), ex.getMessage());
                    errorCode = ErrorCode.INVALID_KEY;
                }
            }
            break;
        }

        if (e.getBindingResult().getFieldErrors().isEmpty() && !e.getBindingResult().getGlobalErrors().isEmpty()) {
            String defaultMessage = e.getBindingResult().getGlobalErrors().get(0).getDefaultMessage();
            try {
                errorCode = ErrorCode.valueOf(defaultMessage);
            } catch (IllegalArgumentException ex) {
                log.error("Global ErrorCode {} does not exist in enum\n{}", defaultMessage, ex.getMessage());
                errorCode = ErrorCode.INVALID_KEY;
            }
        }

        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(
                ApiResponse.<Void>builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse<Void>> AppExceptionHandler(AppException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(
                ApiResponse.<Void>builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
        );
    }
}
