package com.logbook.backend.logbookbe.domain.issue.service;

import com.logbook.backend.logbookbe.domain.issue.controller.dto.AssigneeRequest;
import com.logbook.backend.logbookbe.domain.issue.controller.dto.CreateIssueRequest;
import com.logbook.backend.logbookbe.domain.issue.controller.dto.GetIssueRequest;
import com.logbook.backend.logbookbe.domain.issue.model.Issue;
import com.logbook.backend.logbookbe.domain.issue.repository.IssueRepository;
import com.logbook.backend.logbookbe.domain.issue.type.Status;
import com.logbook.backend.logbookbe.domain.member.model.Member;
import com.logbook.backend.logbookbe.domain.member.service.MemberService;
import com.logbook.backend.logbookbe.domain.member.type.PermissionLevel;
import com.logbook.backend.logbookbe.domain.project.model.Project;
import com.logbook.backend.logbookbe.domain.project.repository.ProjectRepository;
import com.logbook.backend.logbookbe.domain.user.model.User;
import com.logbook.backend.logbookbe.domain.user.type.Vendor;
import net.bytebuddy.asm.Advice;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class IssueServiceTest {

    @Mock
    private IssueRepository issueRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private MemberService memberService;

    @InjectMocks
    private IssueService issueService;

    private Issue sampleIssue;
    private Project sampleProject;
    private Member sampleMember;


    @BeforeEach
    void setUp() {
        UUID randomUUID = UUID.randomUUID();
        UUID randomProjectId = UUID.randomUUID();
        UUID randomMemberId = UUID.randomUUID();
        UUID randomUserId = UUID.randomUUID();

        sampleProject = new Project(randomProjectId, "Test Project", "This is the test project", true);
        sampleMember = new Member(randomMemberId, PermissionLevel.관리자, sampleProject, new User(randomUserId, null, "testUser", "sample@example.com", "IT보안", "testPassword"), null);
        sampleIssue = new Issue(
                randomUUID,
                sampleProject,
                sampleMember,
                "Test Issue",
                "Test issue description",
                Status.진행중,
                Timestamp.from(Instant.now()),
                Timestamp.from(Instant.now())
        );
    }

    @Test
    void testGetIssueById() {
        when(issueRepository.findByIssueId(sampleIssue.getIssueId())).thenReturn(sampleIssue);

        GetIssueRequest result = issueService.getIssueById(sampleIssue.getIssueId());

        assertThat(result.getIssueId()).isEqualTo(sampleIssue.getIssueId());
        assertThat(result.getIssueTitle()).isEqualTo(sampleIssue.getIssueTitle());
        assertThat(result.getIssueDescription()).isEqualTo(sampleIssue.getIssueDescription());
        assertThat(result.getStatus()).isEqualTo(sampleIssue.getStatus());
        assertThat(result.getStartDate()).isEqualTo(sampleIssue.getStartDate());
        assertThat(result.getEndDate()).isEqualTo(sampleIssue.getEndDate());
    }

    @Test
    void testCreateIssue() {
        CreateIssueRequest issueDTO = new CreateIssueRequest();
        AssigneeRequest assigneeDTO = new AssigneeRequest();
        assigneeDTO.setAssigneeId(sampleMember.getMemberId());
        issueDTO.setAssignee(assigneeDTO);
        issueDTO.setIssueTitle(sampleIssue.getIssueTitle());
        issueDTO.setIssueDescription(sampleIssue.getIssueDescription());
        issueDTO.setStatus(sampleIssue.getStatus());
        issueDTO.setStartDate(sampleIssue.getStartDate());
        issueDTO.setEndDate(sampleIssue.getEndDate());

        when(projectRepository.findByProjectId(sampleProject.getProjectId())).thenReturn(Optional.of(sampleProject));
        when(memberService.findAssigneeById(assigneeDTO.getAssigneeId())).thenReturn(sampleMember);
        when(issueRepository.save(any(Issue.class))).thenReturn(sampleIssue);

        UUID result = issueService.createIssue(issueDTO, sampleProject.getProjectId());

        assertThat(result).isEqualTo(sampleIssue.getIssueId());
    }

    @Test
    void testFilterIssues() {
        when(issueRepository.findByProjectProjectId(sampleProject.getProjectId()))
                .thenReturn(Collections.singletonList(sampleIssue));

        List<GetIssueRequest> results = issueService.filterIssues(sampleProject.getProjectId(), sampleMember.getUser().getUserName(), Status.진행중);

        assertThat(results).isNotEmpty();
        assertThat(results.get(0).getIssueId()).isEqualTo(sampleIssue.getIssueId());

        results = issueService.filterIssues(sampleProject.getProjectId(), "nonExistingUser", Status.진행중);
        assertThat(results).isEmpty();

        results = issueService.filterIssues(sampleProject.getProjectId(), sampleMember.getUser().getUserName(), Status.완료);
        assertThat(results).isEmpty();

    }

}
