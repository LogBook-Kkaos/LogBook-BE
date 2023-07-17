package com.logbook.backend.logbookbe.domain.user.exception;

import com.logbook.backend.logbookbe.global.error.exception.ServiceException;
import com.logbook.backend.logbookbe.global.error.ErrorCode;

public class UserNotFoundException extends ServiceException {
    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
