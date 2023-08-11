package com.logbook.backend.logbookbe.releaseContent.repository;

import com.logbook.backend.logbookbe.domain.releaseNote.model.ReleaseNote;
import com.logbook.backend.logbookbe.releaseContent.model.ReleaseContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReleaseContentRepository extends JpaRepository<ReleaseContent, UUID> {

    @Query("SELECT rc FROM ReleaseContent rc WHERE rc.releaseNote.releaseNoteId = :releaseNoteId")
    List<ReleaseContent> findByReleaseNoteReleaseNoteId(@Param("releaseNoteId") UUID releaseNoteId);

    List<ReleaseContent> findAllByReleaseNote(ReleaseNote releaseNote);

    Optional<ReleaseContent> findByReleaseContentId(UUID releaseContentId);



}
