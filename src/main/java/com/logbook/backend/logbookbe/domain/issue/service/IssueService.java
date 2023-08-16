
package com.logbook.backend.logbookbe.domain.issue.service;

import com.logbook.backend.logbookbe.domain.issue.controller.dto.*;
import com.logbook.backend.logbookbe.domain.issue.model.Issue;
import com.logbook.backend.logbookbe.domain.issue.repository.IssueRepository;
import com.logbook.backend.logbookbe.domain.issue.type.Status;
import com.logbook.backend.logbookbe.domain.member.model.Member;
import com.logbook.backend.logbookbe.domain.member.service.MemberService;
import com.logbook.backend.logbookbe.domain.project.model.Project;
import com.logbook.backend.logbookbe.domain.project.repository.ProjectRepository;
import com.logbook.backend.logbookbe.domain.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private MemberService memberService;


    public List<GetIssueRequest> getAllIssues(UUID projectId) {
        List<Issue> issues = issueRepository.findByProjectProjectId(projectId);
        List<GetIssueRequest> issueDTOs = new ArrayList<>();

        for (Issue issue : issues) {
            GetIssueRequest issueDTO = new GetIssueRequest();
            issueDTO.setIssueId(issue.getIssueId());

            Member assignee = issue.getAssignee();
            AssigneeRequest assigneeDTO = new AssigneeRequest();
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

    public GetIssueRequest getIssueById(UUID issueId) {
        Issue issue = issueRepository.findByIssueId(issueId);
        GetIssueRequest issueDTO = new GetIssueRequest();

        issueDTO.setIssueId(issue.getIssueId());
        issueDTO.setIssueTitle(issue.getIssueTitle());
        issueDTO.setIssueDescription(issue.getIssueDescription());
        issueDTO.setStatus(issue.getStatus());
        issueDTO.setStartDate(issue.getStartDate());
        issueDTO.setEndDate(issue.getEndDate());

        Member assignee = issue.getAssignee();
        AssigneeRequest assigneeDTO = new AssigneeRequest();
        assigneeDTO.setAssigneeId(assignee.getMemberId());

        User user = assignee.getUser();
        String username = user.getUserName();
        assigneeDTO.setUserName(username);

        issueDTO.setAssignee(assigneeDTO);

        return issueDTO;

    }

    public UUID createIssue(CreateIssueRequest issueDTO, UUID projectId) {
        Issue issue = new Issue();

        Member assignee = memberService.findAssigneeById(issueDTO.getAssignee().getAssigneeId());
        issue.setAssignee(assignee);

        issue.setIssueTitle(issueDTO.getIssueTitle());
        issue.setIssueDescription(issueDTO.getIssueDescription());
        issue.setStatus(issueDTO.getStatus());
        issue.setStartDate(issueDTO.getStartDate());
        issue.setEndDate(issueDTO.getEndDate());

        Optional<Project> projectOptional = projectRepository.findByProjectId(projectId);

        if (projectOptional.isPresent()) {
            issue.setProject(projectOptional.get());
        } else {
            throw new RuntimeException("해당하는 프로젝트가 없습니다.");
        }

        Issue savedIssue = issueRepository.save(issue);

        return savedIssue.getIssueId();
    }

    public CreateIssueRequest updateIssue(UUID issueId, CreateIssueRequest updatedIssueDTO) {
        Optional<Issue> existingIssueOptional = issueRepository.findById(issueId);

        if (existingIssueOptional.isPresent()) {
            Issue existingIssue = existingIssueOptional.get();

            Member assignee = memberService.findAssigneeById(updatedIssueDTO.getAssignee().getAssigneeId());
            existingIssue.setAssignee(assignee);

            existingIssue.setIssueTitle(updatedIssueDTO.getIssueTitle());
            existingIssue.setIssueDescription(updatedIssueDTO.getIssueDescription());
            existingIssue.setStatus(updatedIssueDTO.getStatus());
            existingIssue.setStartDate(updatedIssueDTO.getStartDate());
            existingIssue.setEndDate(updatedIssueDTO.getEndDate());

            Issue updatedIssue = issueRepository.save(existingIssue);


            CreateIssueRequest responseDTO = new CreateIssueRequest();

            AssigneeRequest assigneeRequest = new AssigneeRequest();
            assigneeRequest.setAssigneeId(assignee.getMemberId());
            assigneeRequest.setUserName(assignee.getUser().getUserName());
            responseDTO.setAssignee(assigneeRequest);

            responseDTO.setIssueTitle(updatedIssue.getIssueTitle());
            responseDTO.setIssueDescription(updatedIssue.getIssueDescription());
            responseDTO.setStatus(updatedIssue.getStatus());
            responseDTO.setStartDate(updatedIssue.getStartDate());
            responseDTO.setEndDate(updatedIssue.getEndDate());

            return responseDTO;
        } else {
            throw new NotFoundException("해당하는 이슈가 없습니다.");
        }

    }

    public DeleteIssueResponse deleteIssue(UUID issueId) {
        issueRepository.deleteById(issueId);
        return new DeleteIssueResponse(issueId, "이슈가 성공적으로 삭제되었습니다.");
    }

    public List<GetIssueRequest> filterIssues(UUID projectId, String userName, Status status) {
        List<Issue> issues = issueRepository.findByProjectProjectId(projectId);

        if (userName != null && !userName.isEmpty()) {
            issues = issues.stream()
                    .filter(issue -> issue.getAssignee() != null &&
                            issue.getAssignee().getUser() != null &&
                            userName.equalsIgnoreCase(issue.getAssignee().getUser().getUserName()))
                    .collect(Collectors.toList());
        }

        if (status != null) {
            issues = issues.stream()
                    .filter(issue -> status.equals(issue.getStatus()))
                    .collect(Collectors.toList());
        }

        return issues.stream()
                .map(issue -> {
                    GetIssueRequest filterIssueDTO = new GetIssueRequest();

                    filterIssueDTO.setIssueId(issue.getIssueId());
                    filterIssueDTO.setIssueTitle(issue.getIssueTitle());
                    filterIssueDTO.setIssueDescription(issue.getIssueDescription());
                    filterIssueDTO.setStatus(issue.getStatus());
                    filterIssueDTO.setStartDate(issue.getStartDate());
                    filterIssueDTO.setEndDate(issue.getEndDate());

                    if (issue.getAssignee() != null) {
                        AssigneeRequest assignee = new AssigneeRequest();
                        assignee.setAssigneeId(issue.getAssignee().getMemberId());
                        assignee.setUserName(issue.getAssignee().getUser().getUserName());

                        filterIssueDTO.setAssignee(assignee);
                    }

                    return filterIssueDTO;
                })
                .collect(Collectors.toList());
    }

    public CreateIssueRequest updateIssueStatus(UUID issueId, UpdateIssueStatusRequest updateIssueStatusDTO) {
        Optional<Issue> existingIssueOptional = issueRepository.findById(issueId);

        if (existingIssueOptional.isPresent()) {
            Issue existingIssue = existingIssueOptional.get();
            existingIssue.setStatus(updateIssueStatusDTO.getStatus());
            Issue updatedIssue = issueRepository.save(existingIssue);

            CreateIssueRequest responseDTO = new CreateIssueRequest();

            AssigneeRequest assigneeRequest = new AssigneeRequest();
            assigneeRequest.setAssigneeId(updatedIssue.getAssignee().getMemberId());
            assigneeRequest.setUserName(updatedIssue.getAssignee().getUser().getUserName());
            responseDTO.setAssignee(assigneeRequest);

            responseDTO.setIssueTitle(updatedIssue.getIssueTitle());
            responseDTO.setIssueDescription(updatedIssue.getIssueDescription());
            responseDTO.setStatus(updatedIssue.getStatus());
            responseDTO.setStartDate(updatedIssue.getStartDate());
            responseDTO.setEndDate(updatedIssue.getEndDate());

            return responseDTO;
        } else {
            throw new NotFoundException("해당하는 이슈가 없습니다.");
        }
    }

    public CreateIssueRequest updateIssueAssignee(UUID issueId, UpdateIssueAssigneeRequest updateIssueAssigneeDTO) {
        Optional<Issue> existingIssueOptional = issueRepository.findById(issueId);

        if (existingIssueOptional.isPresent()) {
            Issue existingIssue = existingIssueOptional.get();
            Member assignee = memberService.findAssigneeById(updateIssueAssigneeDTO.getAssigneeId());
            existingIssue.setAssignee(assignee);
            Issue updatedIssue = issueRepository.save(existingIssue);

            CreateIssueRequest responseDTO = new CreateIssueRequest();

            AssigneeRequest assigneeRequest = new AssigneeRequest();
            assigneeRequest.setAssigneeId(updatedIssue.getAssignee().getMemberId());
            assigneeRequest.setUserName(updatedIssue.getAssignee().getUser().getUserName());
            responseDTO.setAssignee(assigneeRequest);

            responseDTO.setIssueTitle(updatedIssue.getIssueTitle());
            responseDTO.setIssueDescription(updatedIssue.getIssueDescription());
            responseDTO.setStatus(updatedIssue.getStatus());
            responseDTO.setStartDate(updatedIssue.getStartDate());
            responseDTO.setEndDate(updatedIssue.getEndDate());

            return responseDTO;
        } else {
            throw new NotFoundException("해당하는 이슈가 없습니다.");
        }
    }

    public CreateIssueRequest updateIssueStartDate(UUID issueId, UpdateIssueStartDateRequest updateIssueStartDateDTO ) {
        Optional<Issue> existingIssueOptional = issueRepository.findById(issueId);

        if (existingIssueOptional.isPresent()) {
            Issue existingIssue = existingIssueOptional.get();
            existingIssue.setStartDate(updateIssueStartDateDTO.getStartDate());
            Issue updatedIssue = issueRepository.save(existingIssue);

            CreateIssueRequest responseDTO = new CreateIssueRequest();

            AssigneeRequest assigneeRequest = new AssigneeRequest();
            assigneeRequest.setAssigneeId(updatedIssue.getAssignee().getMemberId());
            assigneeRequest.setUserName(updatedIssue.getAssignee().getUser().getUserName());
            responseDTO.setAssignee(assigneeRequest);

            responseDTO.setIssueTitle(updatedIssue.getIssueTitle());
            responseDTO.setIssueDescription(updatedIssue.getIssueDescription());
            responseDTO.setStatus(updatedIssue.getStatus());
            responseDTO.setStartDate(updatedIssue.getStartDate());
            responseDTO.setEndDate(updatedIssue.getEndDate());

            return responseDTO;
        } else {
            throw new NotFoundException("해당하는 이슈가 없습니다.");
        }
    }

    public CreateIssueRequest updateIssueEndDate(UUID issueId, UpdateIssueEndDateRequest updateIssueEndDateDTO ) {
        Optional<Issue> existingIssueOptional = issueRepository.findById(issueId);

        if (existingIssueOptional.isPresent()) {
            Issue existingIssue = existingIssueOptional.get();
            existingIssue.setEndDate(updateIssueEndDateDTO.getEndDate());
            Issue updatedIssue = issueRepository.save(existingIssue);

            CreateIssueRequest responseDTO = new CreateIssueRequest();

            AssigneeRequest assigneeRequest = new AssigneeRequest();
            assigneeRequest.setAssigneeId(updatedIssue.getAssignee().getMemberId());
            assigneeRequest.setUserName(updatedIssue.getAssignee().getUser().getUserName());
            responseDTO.setAssignee(assigneeRequest);

            responseDTO.setIssueTitle(updatedIssue.getIssueTitle());
            responseDTO.setIssueDescription(updatedIssue.getIssueDescription());
            responseDTO.setStatus(updatedIssue.getStatus());
            responseDTO.setStartDate(updatedIssue.getStartDate());
            responseDTO.setEndDate(updatedIssue.getEndDate());

            return responseDTO;
        } else {
            throw new NotFoundException("해당하는 이슈가 없습니다.");
        }
    }

}