package com.logbook.backend.logbookbe.domain.project.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PublicProjectResponse {
    private String projectName;
    private String projectDescription;
}
