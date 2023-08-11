package com.logbook.backend.logbookbe.domain.releaseNote.repository;

import com.logbook.backend.logbookbe.domain.document.model.Document;
import com.logbook.backend.logbookbe.domain.releaseNote.model.ReleaseNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReleaseNoteRepository extends JpaRepository<ReleaseNote, UUID> {

    List<ReleaseNote> findByProjectProjectId(UUID projectId);

    ReleaseNote findByReleaseNoteId(UUID releaseNoteId);

    Optional<ReleaseNote> findById(UUID releaseNoteId);

}

