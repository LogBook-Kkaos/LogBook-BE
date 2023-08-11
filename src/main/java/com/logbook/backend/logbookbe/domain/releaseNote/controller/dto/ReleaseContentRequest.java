package com.logbook.backend.logbookbe.domain.releaseNote.controller.dto;

import com.logbook.backend.logbookbe.releaseContent.type.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ReleaseContentRequest {

    private UUID releaseContentId;
    private String releaseSummary;
    private Category category;
    private String documentLink;
}
