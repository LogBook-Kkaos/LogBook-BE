package com.logbook.backend.logbookbe.releaseContent.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class ReleaseContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long releaseNoteId;
    private Long documentId;
    private String releaseSummary;
    private String category; // 추후 ENUM 타입으로 변경?
    private String documentLink;
}
