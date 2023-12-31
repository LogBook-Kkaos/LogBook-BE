package com.logbook.backend.logbookbe.domain.issue.controller;

import com.logbook.backend.logbookbe.domain.issue.controller.dto.*;
import com.logbook.backend.logbookbe.domain.issue.model.Issue;
import com.logbook.backend.logbookbe.domain.issue.service.IssueService;
import com.logbook.backend.logbookbe.domain.issue.type.Status;
import com.logbook.backend.logbookbe.global.error.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects/{projectId}/issues")
public class IssueController {

    @Autowired
    private IssueService issueService;



    @GetMapping
    @Operation(summary = "모든 이슈 목록 조회", description = "모든 이슈의 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Issue.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<List<GetIssueRequest>> getAllIssues(@PathVariable UUID projectId) {
        List<GetIssueRequest> getAllIssues = issueService.getAllIssues(projectId);
        return ResponseEntity.ok(getAllIssues);
    }

    @GetMapping("/{issueId}")
    @Operation(summary = "특정 이슈 상세 조회", description = "특정 이슈의 상세 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Issue.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<GetIssueRequest> getIssueById(@PathVariable UUID projectId, @PathVariable UUID issueId) {
        GetIssueRequest getIssueById = issueService.getIssueById(issueId);
        return new ResponseEntity<>(getIssueById, HttpStatus.CREATED);
    }

    @PostMapping
    @Operation(summary = "새로운 이슈 생성", description = "새로운 이슈를 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = Issue.class))),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public UUID createIssue(@RequestBody CreateIssueRequest issueDTO, @PathVariable UUID projectId) {
        UUID createdIssueRequest = issueService.createIssue(issueDTO, projectId);
        return createdIssueRequest;
    }

    @PutMapping("/{issueId}")
    @Operation(summary = "특정 이슈 정보 업데이트", description = "특정 이슈의 정보를 업데이트합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Issue.class))),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<CreateIssueRequest> updateIssue(@PathVariable UUID issueId, @RequestBody CreateIssueRequest updatedIssue) {
        CreateIssueRequest updatedIssueResult = issueService.updateIssue(issueId, updatedIssue);
        return new ResponseEntity<>(updatedIssueResult, HttpStatus.OK);
    }

    @PutMapping("/{issueId}/assignee")
    @Operation(summary = "특정 이슈 담당자 변경", description = "특정 이슈의 담당자를 변경합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Issue.class))),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<CreateIssueRequest> updateIssueAssignee(@PathVariable UUID issueId, @RequestBody UpdateIssueAssigneeRequest updatedIssue) {
        CreateIssueRequest updatedIssueResult = issueService.updateIssueAssignee(issueId, updatedIssue);
        return new ResponseEntity<>(updatedIssueResult, HttpStatus.OK);
    }


    @PutMapping("/{issueId}/status")
    @Operation(summary = "특정 이슈 상태 변경", description = "특정 이슈의 상태를 변경합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Issue.class))),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<CreateIssueRequest> updateIssueStatus(@PathVariable UUID issueId, @RequestBody UpdateIssueStatusRequest updatedIssue) {
        CreateIssueRequest updatedIssueResult = issueService.updateIssueStatus(issueId, updatedIssue);
        return new ResponseEntity<>(updatedIssueResult, HttpStatus.OK);
    }


    @PutMapping("/{issueId}/startDate")
    @Operation(summary = "특정 이슈 시작일 변경", description = "특정 이슈의 시작일을 변경합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Issue.class))),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<CreateIssueRequest> updateIssueStartDate(@PathVariable UUID issueId, @RequestBody UpdateIssueStartDateRequest updatedIssue) {
        CreateIssueRequest updatedIssueResult = issueService.updateIssueStartDate(issueId, updatedIssue);
        return new ResponseEntity<>(updatedIssueResult, HttpStatus.OK);
    }

    @PutMapping("/{issueId}/endDate")
    @Operation(summary = "특정 이슈 종료일 변경", description = "특정 이슈의 종료일을 변경합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Issue.class))),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<CreateIssueRequest> updateIssueEndDate(@PathVariable UUID issueId, @RequestBody UpdateIssueEndDateRequest updatedIssue) {
        CreateIssueRequest updatedIssueResult = issueService.updateIssueEndDate(issueId, updatedIssue);
        return new ResponseEntity<>(updatedIssueResult, HttpStatus.OK);
    }


    @DeleteMapping("/{issueId}")
    @Operation(summary = "특정 이슈 삭제", description = "특정 이슈를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Issue.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public DeleteIssueResponse deleteIssue(@PathVariable UUID issueId) {
        return issueService.deleteIssue(issueId);
    }

    @GetMapping("/filter")
    @Operation(summary = "이슈 필터링", description = "담당자와 상태를 기준으로 이슈를 필터링합니다.")@ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Issue.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public List<GetIssueRequest> filterIssues(
            @PathVariable UUID projectId,
            @RequestParam(required = false) String assigneeName,
            @RequestParam(required = false) Status status) {
        return issueService.filterIssues(projectId, assigneeName, status);
    }



}
