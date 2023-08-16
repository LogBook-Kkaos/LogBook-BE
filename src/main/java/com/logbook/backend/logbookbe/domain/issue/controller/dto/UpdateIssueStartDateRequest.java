package com.logbook.backend.logbookbe.domain.issue.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class UpdateIssueStartDateRequest {
    private Timestamp startDate;
}
