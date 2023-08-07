package com.logbook.backend.logbookbe.domain.issue.controller.dto;

import com.logbook.backend.logbookbe.domain.document.model.DocumentFile;
import com.logbook.backend.logbookbe.domain.issue.type.Status;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class getIssueRequest {
    private UUID issueId;

    private assigneeRequest assignee;

    private String issueTitle;

    private String issueDescription;

    private Status status;

    private Timestamp startDate;

    private Timestamp endDate;
}
