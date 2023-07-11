package com.logbook.backend.logbookbe.global.error.exception;

import com.logbook.backend.logbookbe.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {
    private final ErrorCode errorCode;

    public ServiceException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}