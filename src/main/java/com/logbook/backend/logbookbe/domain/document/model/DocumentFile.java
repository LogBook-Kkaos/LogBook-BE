package com.logbook.backend.logbookbe.domain.document.model;
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

    @Lob
    @Column(name = "file_data", nullable = false)
    private byte[] fileData;

    @Column(name = "file_type", length = 20, nullable = false)
    private String fileType;

    @Column(name = "file_name", length = 20, nullable = false)
    private String fileName;

    @ManyToOne
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

}
