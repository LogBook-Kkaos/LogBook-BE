package com.logbook.backend.logbookbe.domain.project.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ReleaseNoteRequest {

    private UUID releaseNoteId;

    private String releaseTitle;
}
