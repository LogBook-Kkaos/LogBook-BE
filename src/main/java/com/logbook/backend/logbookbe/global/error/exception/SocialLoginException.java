package com.logbook.backend.logbookbe.global.error.exception;

import com.logbook.backend.logbookbe.global.error.ErrorCode;

public class SocialLoginException extends ServiceException {
    public SocialLoginException() {
        super(ErrorCode.ONLY_SOCIAL_LOGIN);
    }
}