package com.logbook.backend.logbookbe.domain.user.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProjectInfoResponse {
    private UUID projectId;
    private String projectName;
}
