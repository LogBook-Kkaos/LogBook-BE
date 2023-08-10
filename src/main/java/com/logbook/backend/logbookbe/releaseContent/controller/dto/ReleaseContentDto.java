package com.logbook.backend.logbookbe.releaseContent.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReleaseContentDto {

    private Long releaseNoteId;
    private Long documentId;
    private String releaseSummary;
    private String category;
    private String documentLink;

    public ReleaseContentDto() {
    }

    public ReleaseContentDto(Long releaseNoteId, Long documentId, String releaseSummary, String category, String documentLink) {
        this.releaseNoteId = releaseNoteId;
        this.documentId = documentId;
        this.releaseSummary = releaseSummary;
        this.category = category;
        this.documentLink = documentLink;
    }


}
