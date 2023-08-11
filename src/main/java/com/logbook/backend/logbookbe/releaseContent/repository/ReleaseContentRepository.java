package com.logbook.backend.logbookbe.releaseContent.repository;

import com.logbook.backend.logbookbe.domain.releaseNote.model.ReleaseNote;
import com.logbook.backend.logbookbe.releaseContent.model.ReleaseContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReleaseContentRepository extends JpaRepository<ReleaseContent, UUID> {

    List<ReleaseContent> findByReleaseNoteReleaseNoteId(UUID releaseNoteId);

    ReleaseContent findByReleaseContentId(UUID releaseContentId);

}
