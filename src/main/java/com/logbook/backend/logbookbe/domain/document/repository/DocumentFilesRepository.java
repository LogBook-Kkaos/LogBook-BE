package com.logbook.backend.logbookbe.domain.document.repository;

import com.logbook.backend.logbookbe.domain.document.model.Document;
import com.logbook.backend.logbookbe.domain.document.model.DocumentFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentFilesRepository extends JpaRepository<DocumentFile, Integer> {

}
