package com.logbook.backend.logbookbe.domain.document.model;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "DocumentFile")
public class DocumentFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Integer fileId;

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
