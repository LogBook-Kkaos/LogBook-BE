package com.logbook.backend.logbookbe.domain.issue.controller;

import com.logbook.backend.logbookbe.domain.issue.controller.dto.IssueDeleteResponse;
import com.logbook.backend.logbookbe.domain.issue.model.Issue;
import com.logbook.backend.logbookbe.domain.issue.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/issues")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @GetMapping
    public List<Issue> getAllIssues(@PathVariable Integer projectId) {
        return issueService.getAllIssues(projectId);
    }

    @GetMapping("/{issueId}")
    public Issue getIssueById(@PathVariable Integer issueId) {
        return issueService.getIssueById(issueId);
    }

    @PostMapping
    public Issue createIssue(@PathVariable Integer projectId, @RequestBody Issue issue) {
        return issueService.createIssue(projectId, issue);
    }

    @PutMapping("/{issueId}")
    public Issue updateIssue(@PathVariable Integer issueId, @RequestBody Issue updatedIssue) {
        return issueService.updateIssue(issueId, updatedIssue);
    }

    @DeleteMapping("/{issueId}")
    public IssueDeleteResponse deleteIssue(@PathVariable Integer issueId) {
        return issueService.deleteIssue(issueId);
    }

    @GetMapping("/filter")
    public List<Issue> filterIssues(
            @PathVariable Integer projectId,
            @RequestParam(required = false) Integer assigneeId,
            @RequestParam(required = false) String status) {
        return issueService.filterIssues(projectId, assigneeId, status);
    }

}
