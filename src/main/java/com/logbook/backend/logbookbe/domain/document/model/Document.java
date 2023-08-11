package com.logbook.backend.logbookbe.domain.document.model;

import com.logbook.backend.logbookbe.domain.project.model.Project;
import com.logbook.backend.logbookbe.domain.user.type.Vendor;
import com.logbook.backend.logbookbe.releaseContent.model.ReleaseContent;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "Document")
public class Document{
    @Id
    @GeneratedValue
    @Type(type="uuid-char")
    @Column(name = "document_id", columnDefinition = "VARCHAR(36)")
    private UUID documentId = UUID.randomUUID();

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

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReleaseContent> releaseContents = new ArrayList<>();

}