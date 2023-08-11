package com.logbook.backend.logbookbe.domain.user.exception;

import com.logbook.backend.logbookbe.global.error.ErrorCode;
import com.logbook.backend.logbookbe.global.error.exception.ServiceException;

public class UserNotFoundException extends ServiceException {
    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
