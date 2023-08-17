package com.logbook.backend.logbookbe.domain.document.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "DocumentFile")
public class DocumentFile {
    @Id
    @GeneratedValue
    @Type(type="uuid-char")
    @Column(name = "file_id", columnDefinition = "VARCHAR(36)")
    private UUID fileId = UUID.randomUUID();

    @Column(name = "image_url", length=1000)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "document_id", nullable = false)
    @JsonIgnoreProperties("documentFiles")
    private Document document;

}
