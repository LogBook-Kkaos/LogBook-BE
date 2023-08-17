package com.logbook.backend.logbookbe.domain.issue.controller.dto;

import com.logbook.backend.logbookbe.domain.issue.type.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateIssueStatusRequest {
    private Status status;
}
