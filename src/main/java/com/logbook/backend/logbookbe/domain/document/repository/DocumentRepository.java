package com.logbook.backend.logbookbe.domain.document.repository;

import com.logbook.backend.logbookbe.domain.document.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.net.InterfaceAddress;
import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Integer> {
    List<Document> findByProjectProjectId(Integer projectId);

    Document findByDocumentId(Integer documentId);
}