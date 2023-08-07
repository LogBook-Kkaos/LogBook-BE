package com.logbook.backend.logbookbe.domain.issue.controller.dto;

import com.logbook.backend.logbookbe.domain.user.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class assigneeRequest {
    private UUID assigneeId;

    private String userName;
}
