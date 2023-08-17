package com.logbook.backend.logbookbe.domain.document.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class createDocumentFilesRequest {

    private List<String> imageUrlList;

}
