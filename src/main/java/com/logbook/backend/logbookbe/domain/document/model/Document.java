package com.logbook.backend.logbookbe.domain.document.model;

import com.logbook.backend.logbookbe.domain.project.model.Project;
import com.logbook.backend.logbookbe.domain.user.type.Vendor;
import lombok.*;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "Document")
public class Document{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_id")
    private Integer documentId;

    @Column(name = "document_title", length = 20, nullable = false)
    private String documentTitle;

    @Lob
    @Column(name = "document_content", nullable = false)
    private String documentContent;

    @Column(name = "creation_date", nullable = false)
    private Timestamp creationDate;

    @OneToMany(mappedBy = "document")
    private List<DocumentFile> documentFiles;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

}