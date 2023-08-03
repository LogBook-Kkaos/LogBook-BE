package com.logbook.backend.logbookbe.domain.document.dto;

import com.logbook.backend.logbookbe.domain.document.model.DocumentFile;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class getDocumentRequest {
    private Integer documentId;

    private String documentTitle;

    private String documentContent;

    private List<DocumentFile> documentFiles;

    private Timestamp creationDate;


}
