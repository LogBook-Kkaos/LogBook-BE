package com.logbook.backend.logbookbe.releaseContent.controller.dto;

import com.logbook.backend.logbookbe.releaseContent.type.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class GetReleaseContentResponse {

    private UUID releaseContentId;

    private DocumentRequest document;

    private String releaseSummary;

    private Category category;

    private String documentLink;
}
