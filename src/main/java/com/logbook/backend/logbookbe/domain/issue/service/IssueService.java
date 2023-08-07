
package com.logbook.backend.logbookbe.domain.issue.service;

import com.logbook.backend.logbookbe.domain.document.dto.createDocumentRequest;
import com.logbook.backend.logbookbe.domain.document.model.Document;
import com.logbook.backend.logbookbe.domain.issue.controller.dto.createIssueRequest;
import com.logbook.backend.logbookbe.domain.issue.controller.dto.deleteIssueResponse;
import com.logbook.backend.logbookbe.domain.issue.model.Issue;
import com.logbook.backend.logbookbe.domain.issue.repository.IssueRepository;
import com.logbook.backend.logbookbe.domain.issue.type.Status;
import com.logbook.backend.logbookbe.domain.project.model.Project;
import com.logbook.backend.logbookbe.domain.project.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class IssueService {

    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private ProjectRepository projectRepository;

    public List<Issue> getAllIssues(UUID projectId) {
        return issueRepository.findByProjectProjectId(projectId);
    }

    public Issue getIssueById(UUID issueId) {
        return issueRepository.findById(issueId)
                .orElseThrow(() -> new NoSuchElementException("해당하는 이슈를 찾을 수 없습니다."));
    }

    public boolean createIssue(createIssueRequest issueDto, UUID projectId) {
        Issue issue = new Issue();
        issue.setAssignee(issueDto.getAssignee());
        issue.setIssueTitle(issueDto.getIssueTitle());
        issue.setIssueDescription(issueDto.getIssueDescription());
        issue.setStatus(issueDto.getStatus());
        issue.setStartDate(issueDto.getStartDate());
        issue.setEndDate(issueDto.getEndDate());

        Optional<Project> projectOptional = projectRepository.findByProjectId(projectId);

        if (projectOptional.isPresent()) {
            issue.setProject(projectOptional.get());
        } else {
            throw new RuntimeException("해당하는 프로젝트가 없습니다.");
        }

        issueRepository.save(issue);

        return true;
    }

    public Issue updateIssue(UUID issueId, Issue updatedIssue) {
        return issueRepository.findById(issueId)
                .map(issue -> {
                    issue.setIssueTitle(updatedIssue.getIssueTitle());
                    issue.setIssueDescription(updatedIssue.getIssueDescription());
                    issue.setStatus(updatedIssue.getStatus());
                    issue.setAssignee(updatedIssue.getAssignee());
                    return issueRepository.save(issue);
                })
                .orElseThrow(() -> new NoSuchElementException("해당하는 이슈를 찾을 수 없습니다."));
    }

    public deleteIssueResponse deleteIssue(UUID issueId) {
        issueRepository.deleteById(issueId);
        return new deleteIssueResponse(issueId, "이슈가 성공적으로 삭제되었습니다.");
    }

    public List<Issue> filterIssues(UUID projectId, UUID assigneeId, Status status) {
        List<Issue> issues = issueRepository.findByProjectProjectId(projectId);

        if (assigneeId != null) {
            issues = issues.stream()
                    .filter(issue -> assigneeId.equals(issue.getAssignee()))
                    .collect(Collectors.toList());
        }

        if (status != null) {
            issues = issues.stream()
                    .filter(issue -> status.equals(issue.getStatus()))
                    .collect(Collectors.toList());
        }

        return issues;
    }

}