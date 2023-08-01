package com.logbook.backend.logbookbe.domain.member.exception;

import com.logbook.backend.logbookbe.global.error.exception.ServiceException;
import com.logbook.backend.logbookbe.global.error.ErrorCode;

public class MemberAlreadyExistsException extends ServiceException {
    public MemberAlreadyExistsException() {
        super(ErrorCode.MEMBER_ALREADY_EXISTS);
    }
}