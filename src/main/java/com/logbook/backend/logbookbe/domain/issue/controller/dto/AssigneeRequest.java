package com.logbook.backend.logbookbe.domain.issue.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AssigneeRequest {
    private UUID assigneeId;

    private String userName;
}
