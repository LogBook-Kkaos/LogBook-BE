package com.logbook.backend.logbookbe.global.error.exception;

import com.logbook.backend.logbookbe.global.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public class ServiceException extends RuntimeException {
    private final ErrorCode errorCode;
}