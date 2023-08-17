package com.logbook.backend.logbookbe.domain.document.repository;

import com.logbook.backend.logbookbe.domain.document.dto.getAllDocumentRequest;
import com.logbook.backend.logbookbe.domain.document.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DocumentRepository extends JpaRepository<Document, Integer> {
    List<Document> findByProjectProjectId(UUID projectId);

    Optional<Document> findByDocumentId(UUID documentId);

    List<Document> findByProjectProjectIdAndDocumentTitleContaining(UUID projectId,String searchString);
}