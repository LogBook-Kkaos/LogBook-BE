package com.logbook.backend.logbookbe.domain.member.exception;

import com.logbook.backend.logbookbe.global.error.ErrorCode;
import com.logbook.backend.logbookbe.global.error.exception.ServiceException;

public class MemberUpdateException extends ServiceException {
    public MemberUpdateException() {
        super(ErrorCode.MEMBER_UPDATE_FAILED);
    }
}