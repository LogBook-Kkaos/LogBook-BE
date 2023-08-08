package com.logbook.backend.logbookbe.domain.releaseNote.repository;

import com.logbook.backend.logbookbe.domain.releaseNote.model.ReleaseNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReleaseNoteRepository extends JpaRepository<ReleaseNote, Long> {

}

