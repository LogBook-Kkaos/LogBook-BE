
package com.logbook.backend.logbookbe.domain.issue.repository;

import com.logbook.backend.logbookbe.domain.issue.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IssueRepository extends JpaRepository<Issue, UUID> {
    List<Issue> findByProjectProjectId(UUID projectId);

}