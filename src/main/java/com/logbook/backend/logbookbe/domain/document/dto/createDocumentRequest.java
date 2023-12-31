package com.logbook.backend.logbookbe.domain.document.dto;

import com.logbook.backend.logbookbe.domain.document.model.DocumentFile;
import com.logbook.backend.logbookbe.domain.project.model.Project;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class createDocumentRequest {

    private String documentTitle;

    private String documentContent;

    List<String> imageUrlList;

}
