package com.logbook.backend.logbookbe.domain.user.exception;

import com.logbook.backend.logbookbe.global.error.ErrorCode;
import com.logbook.backend.logbookbe.global.error.exception.ServiceException;

public class PasswordNotMatchException extends ServiceException {
    public PasswordNotMatchException() {
        super(ErrorCode.PASSWORD_NOT_MATCH);
    }
}
