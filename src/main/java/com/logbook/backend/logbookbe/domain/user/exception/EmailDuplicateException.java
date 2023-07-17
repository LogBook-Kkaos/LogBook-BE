package com.logbook.backend.logbookbe.domain.user.exception;

import com.logbook.backend.logbookbe.global.error.ErrorCode;
import com.logbook.backend.logbookbe.global.error.exception.ServiceException;

public class EmailDuplicateException extends ServiceException {
    public EmailDuplicateException() {
        super(ErrorCode.EMAIL_DUPLICATE);
    }
}