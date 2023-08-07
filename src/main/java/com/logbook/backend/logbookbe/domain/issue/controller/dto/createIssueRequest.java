package com.logbook.backend.logbookbe.domain.issue.controller.dto;

import com.logbook.backend.logbookbe.domain.issue.type.Status;
import com.logbook.backend.logbookbe.domain.member.model.Member;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
public class createIssueRequest {

    private Member assignee;
    private String issueTitle;
    private String issueDescription;
    private Status status;
    private Timestamp startDate;
    private Timestamp endDate;
}
