
package com.logbook.backend.logbookbe.domain.issue.repository;

import com.logbook.backend.logbookbe.domain.issue.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Integer> {
    List<Issue> findByProjectProjectId(Integer projectId);

}