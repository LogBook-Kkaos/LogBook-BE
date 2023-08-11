package com.logbook.backend.logbookbe.domain.releaseNote.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class CreateReleaseNoteRequest {

    private CreatorRequest creator;

    private String releaseTitle;

    private Timestamp creationDate;

    private String version;

    private boolean isImportant;

    private boolean isPublic;

    private ReleaseContentRequest releaseContents;

}
