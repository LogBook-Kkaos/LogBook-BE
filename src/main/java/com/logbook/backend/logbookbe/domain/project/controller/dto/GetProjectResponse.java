package com.logbook.backend.logbookbe.domain.project.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class GetProjectResponse {

    private UUID projectId;

    private String projectName;

    private String projectDescription;

    private boolean isPublic;

    private List<ReleaseNoteRequest> releaseNotes;

    private List<DocumentRequest> documents;

}
