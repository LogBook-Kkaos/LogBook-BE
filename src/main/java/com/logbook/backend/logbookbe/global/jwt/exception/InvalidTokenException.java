package com.logbook.backend.logbookbe.global.jwt.exception;

import com.logbook.backend.logbookbe.global.error.ErrorCode;
import com.logbook.backend.logbookbe.global.error.exception.ServiceException;

public class InvalidTokenException extends ServiceException {
    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}
