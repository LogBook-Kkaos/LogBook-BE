package com.logbook.backend.logbookbe.domain.document.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter

public class getAllDocumentRequest {
    private Integer documentId;

    private String documentTitle;

    private Timestamp creationDate;

}
