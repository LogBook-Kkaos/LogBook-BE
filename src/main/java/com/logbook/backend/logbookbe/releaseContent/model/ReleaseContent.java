package com.logbook.backend.logbookbe.releaseContent.model;

import com.logbook.backend.logbookbe.domain.document.model.Document;
import com.logbook.backend.logbookbe.domain.releaseNote.model.ReleaseNote;
import com.logbook.backend.logbookbe.releaseContent.type.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReleaseContent {

    @Id
    @GeneratedValue
    @Type(type="uuid-char")
    @Column(name = "release_content_id", columnDefinition = "VARCHAR(36)")
    private UUID releaseContentId = UUID.randomUUID();

    @ManyToOne
    @JoinColumn(name = "release_note_id", nullable = false)
    private ReleaseNote releaseNote;

    @ManyToOne
    @JoinColumn(name = "document_id")
    private Document document;

    @Column(name = "release_summary", nullable = false)
    private String releaseSummary;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(name = "document_link")
    private String documentLink;
}
