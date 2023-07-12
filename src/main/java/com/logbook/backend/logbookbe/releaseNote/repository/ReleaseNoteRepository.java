package com.logbook.backend.logbookbe.releaseNote.repository;

import com.logbook.backend.logbookbe.releaseNote.model.ReleaseNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReleaseNoteRepository extends JpaRepository<ReleaseNote, Long> {

}

