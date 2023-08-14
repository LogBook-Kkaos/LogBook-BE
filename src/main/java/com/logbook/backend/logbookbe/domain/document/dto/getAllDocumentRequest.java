package com.logbook.backend.logbookbe.domain.document.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter

public class getAllDocumentRequest {
    private UUID documentId;

    private String documentTitle;

    private Timestamp creationDate;

    private String imageUrl;

}
