
package com.logbook.backend.logbookbe.domain.issue.service;

import com.logbook.backend.logbookbe.domain.document.dto.createDocumentRequest;
import com.logbook.backend.logbookbe.domain.document.dto.getAllDocumentRequest;
import com.logbook.backend.logbookbe.domain.document.model.Document;
import com.logbook.backend.logbookbe.domain.document.repository.DocumentRepository;
import com.logbook.backend.logbookbe.domain.issue.controller.dto.assigneeRequest;
import com.logbook.backend.logbookbe.domain.issue.controller.dto.createIssueRequest;
import com.logbook.backend.logbookbe.domain.issue.controller.dto.deleteIssueResponse;
import com.logbook.backend.logbookbe.domain.issue.controller.dto.getAllIssuesRequest;
import com.logbook.backend.logbookbe.domain.issue.model.Issue;
import com.logbook.backend.logbookbe.domain.issue.repository.IssueRepository;
import com.logbook.backend.logbookbe.domain.issue.type.Status;
import com.logbook.backend.logbookbe.domain.member.model.Member;
import com.logbook.backend.logbookbe.domain.project.model.Project;
import com.logbook.backend.logbookbe.domain.project.repository.ProjectRepository;
import com.logbook.backend.logbookbe.domain.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    public IssueService(IssueRepository issueRepository, ProjectRepository projectRepository) {
        this.issueRepository = issueRepository;
        this.projectRepository = projectRepository;
    }


    public List<getAllIssuesRequest> getAllIssues(UUID projectId) {
        List<Issue> issues = issueRepository.findByProjectProjectId(projectId);
        List<getAllIssuesRequest> issueDTOs = new ArrayList<>();

        for (Issue issue : issues) {
            getAllIssuesRequest issueDTO = new getAllIssuesRequest();
            issueDTO.setIssueId(issue.getIssueId());

            Member assignee = issue.getAssignee();
            assigneeRequest assigneeDTO = new assigneeRequest();
            assigneeDTO.setAssigneeId(assignee.getMemberId());

            User user = assignee.getUser();
            String username = user.getUserName();
            assigneeDTO.setUserName(username);

            issueDTO.setAssignee(assigneeDTO);

            issueDTO.setIssueTitle(issue.getIssueTitle());
            issueDTO.setIssueDescription(issue.getIssueDescription());
            issueDTO.setStatus(issue.getStatus());
            issueDTO.setStartDate(issue.getStartDate());
            issueDTO.setEndDate(issue.getEndDate());

            issueDTOs.add(issueDTO);
        }

        return issueDTOs;
    }

    public Issue getIssueById(UUID issueId) {
        return issueRepository.findById(issueId)
                .orElseThrow(() -> new NoSuchElementException("해당하는 이슈를 찾을 수 없습니다."));
    }

    public UUID createIssue(createIssueRequest issueDto, UUID projectId) {
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

        return issue.getIssueId();
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