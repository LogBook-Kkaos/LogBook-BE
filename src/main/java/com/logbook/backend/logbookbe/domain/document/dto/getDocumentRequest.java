package com.logbook.backend.logbookbe.domain.document.dto;

import com.logbook.backend.logbookbe.domain.document.model.DocumentFile;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class getDocumentRequest {
    private UUID documentId;

    private String documentTitle;

    private String documentContent;

    private List<DocumentFile> documentFiles;

    private Timestamp creationDate;


}
