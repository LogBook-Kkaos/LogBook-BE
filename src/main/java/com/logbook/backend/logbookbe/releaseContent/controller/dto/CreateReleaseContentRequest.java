package com.logbook.backend.logbookbe.releaseContent.controller.dto;

import com.logbook.backend.logbookbe.releaseContent.type.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateReleaseContentRequest {

    private DocumentRequest document;

    private String releaseSummary;

    private Category category;

    private String documentLink;

}
