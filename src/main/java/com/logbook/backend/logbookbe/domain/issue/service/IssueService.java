package com.logbook.backend.logbookbe.domain.issue.service;

import com.logbook.backend.logbookbe.domain.issue.model.Issue;
import com.logbook.backend.logbookbe.domain.issue.repository.IssueRepository;
import com.logbook.backend.logbookbe.domain.project.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Repository
public class IssueService {

    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private ProjectRepository projectRepository;

    public List<Issue> getAllIssues(Integer projectId) {
        return issueRepository.findByProjectId(projectId);
    }

    public Issue getIssueById(Integer issueId) {
        return issueRepository.findById(issueId)
                .orElseThrow(() -> new NoSuchElementException("해당하는 이슈를 찾을 수 없습니다."));
    }

    public Issue createIssue(Integer projectId, Issue issue) {
        return projectRepository.findById(projectId)
                .map(project -> {
                    issue.setProject(project);
                    return issueRepository.save(issue);
                })
                .orElseThrow(() -> new NoSuchElementException("해당하는 프로젝트를 찾을 수 없습니다."));
    }

    public Issue updateIssue(Integer issueId, Issue updatedIssue) {
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

    public void deleteIssue(Integer issueId) {
        issueRepository.deleteById(issueId);
    }


    public List<Issue> filterIssues(Integer projectId, Integer assigneeId, String status) {
        List<Issue> issues = issueRepository.findByProjectId(projectId);

        if (assigneeId != null) {
            issues = issues.stream()
                    .filter(issue -> assigneeId.equals(issue.getAssignee()))
                    .collect(Collectors.toList());
        }

        if (status != null) {
            issues = issues.stream()
                    .filter(issue -> status.equalsIgnoreCase(issue.getStatus()))
                    .collect(Collectors.toList());
        }

        return issues;
    }

}
