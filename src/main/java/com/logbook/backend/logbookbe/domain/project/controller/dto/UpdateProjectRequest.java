package com.logbook.backend.logbookbe.domain.project.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProjectRequest {

    private String projectName;
    private String projectDescription;
    private boolean isPublic;
    private Integer memberCount;

}
