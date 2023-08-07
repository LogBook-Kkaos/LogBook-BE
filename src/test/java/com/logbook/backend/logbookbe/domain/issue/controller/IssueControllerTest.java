package com.logbook.backend.logbookbe.domain.issue.controller;

import com.logbook.backend.logbookbe.domain.issue.controller.dto.IssueDeleteResponse;
import com.logbook.backend.logbookbe.domain.issue.model.Issue;
import com.logbook.backend.logbookbe.domain.issue.service.IssueService;
import com.logbook.backend.logbookbe.domain.issue.type.Status;
import com.logbook.backend.logbookbe.domain.member.model.Member;
import com.logbook.backend.logbookbe.domain.project.model.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IssueControllerTest {

    @InjectMocks
    private IssueController issueController;

    @Mock
    private IssueService issueService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllIssues() {
        UUID mockProjectId = UUID.randomUUID();
        Issue mockIssue = new Issue();
        ArrayList<Issue> mockIssues = new ArrayList<>();
        mockIssues.add(mockIssue);

        when(issueService.getAllIssues(mockProjectId)).thenReturn(mockIssues);

        List<Issue> retrievedIssues = issueController.getAllIssues(mockProjectId);

        assertEquals(mockIssues, retrievedIssues);
    }

    @Test
    public void testGetIssueById() {
        UUID mockProjectId = UUID.randomUUID();

        Project mockProject = new Project();

        mockProject.setProjectId(mockProjectId);

        Issue mockIssue = Issue.builder()
                .project(mockProject)
                .issueTitle("Test issue")
                .build();

        List<Issue> mockIssues = new ArrayList<>();
        mockIssues.add(mockIssue);

        when(issueService.getAllIssues(mockProjectId)).thenReturn(mockIssues);

        List<Issue> retrievedIssues = issueController.getAllIssues(mockProjectId);

        assertEquals(mockIssues, retrievedIssues);

    }

    @Test
    public void testCreateIssue() {
        UUID mockProjectId = UUID.randomUUID();

        Project mockProject = new Project();

        mockProject.setProjectId(mockProjectId);

        Issue issueToCreate = Issue.builder()
                .project(mockProject)
                .issueTitle("Test issue")
                .build();

        when(issueService.createIssue(mockProjectId, issueToCreate)).thenReturn(issueToCreate);

        Issue response = issueController.createIssue(mockProjectId, issueToCreate);

        assertEquals(issueToCreate, response);
    }

    @Test
    public void testUpdateIssue() {
        UUID mockIssueId = UUID.randomUUID();

        Issue existingIssue = Issue.builder()
                .issueId(mockIssueId)
                .issueTitle("Existing issue")
                .build();

        Issue updatedIssue = Issue.builder()
                .issueId(mockIssueId)
                .issueTitle("Updated issue")
                .build();

        when(issueService.updateIssue(mockIssueId, updatedIssue)).thenReturn(updatedIssue);

        Issue response = issueController.updateIssue(mockIssueId, updatedIssue);

        assertEquals(updatedIssue, response);
    }

    @Test
    public void testDeleteIssue() {
        UUID mockIssueId = UUID.randomUUID();

        IssueDeleteResponse expectedResponse = new IssueDeleteResponse();
        expectedResponse.setIssueId(mockIssueId);
        expectedResponse.setMessage("Issue deleted successfully");

        when(issueService.deleteIssue(mockIssueId)).thenReturn(expectedResponse);

        IssueDeleteResponse response = issueController.deleteIssue(mockIssueId);

        assertEquals(expectedResponse, response);
    }

    @Test
    public void testFilterIssues() {
        UUID mockProjectId = UUID.randomUUID();
        UUID mockAssigneeId = UUID.randomUUID();
        Status mockStatus = Status.할일;

        Member mockMember = new Member();

        mockMember.setMemberId(mockAssigneeId);

        Issue issue1 = Issue.builder()
                .issueId(UUID.randomUUID())
                .issueTitle("Test issue 1")
                .assignee(mockMember)
                .status(mockStatus)
                .build();

        Issue issue2 = Issue.builder()
                .issueId(UUID.randomUUID())
                .issueTitle("Test issue 2")
                .assignee(mockMember)
                .status(mockStatus)
                .build();

        List<Issue> expectedFilteredIssues = Arrays.asList(issue1, issue2);

        when(issueService.filterIssues(mockProjectId, mockAssigneeId, mockStatus)).thenReturn(expectedFilteredIssues);

        List<Issue> response = issueController.filterIssues(mockProjectId, mockAssigneeId, mockStatus);

        assertEquals(expectedFilteredIssues, response);
    }




}
