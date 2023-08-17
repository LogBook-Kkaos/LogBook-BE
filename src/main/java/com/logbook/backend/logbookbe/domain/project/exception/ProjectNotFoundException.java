package com.logbook.backend.logbookbe.domain.project.exception;

import com.logbook.backend.logbookbe.global.error.exception.ServiceException;
import com.logbook.backend.logbookbe.global.error.ErrorCode;

public class ProjectNotFoundException extends ServiceException {
    public ProjectNotFoundException() {
        super(ErrorCode.PROJECT_NOT_FOUND);
    }
}
