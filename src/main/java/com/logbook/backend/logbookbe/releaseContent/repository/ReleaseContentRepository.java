package com.logbook.backend.logbookbe.releaseContent.repository;

import com.logbook.backend.logbookbe.releaseContent.model.ReleaseContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReleaseContentRepository extends JpaRepository<ReleaseContent, Long> {
}
