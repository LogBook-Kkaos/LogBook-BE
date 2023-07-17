package com.logbook.backend.logbookbe.domain.user.exception;

import com.logbook.backend.logbookbe.global.error.ErrorCode;
import com.logbook.backend.logbookbe.global.error.exception.ServiceException;

public class PasswordInvalidException extends ServiceException {
    public PasswordInvalidException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}