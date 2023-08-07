package com.logbook.backend.logbookbe.domain.issue.controller;

import com.logbook.backend.logbookbe.domain.document.dto.createDocumentRequest;
import com.logbook.backend.logbookbe.domain.document.dto.getAllDocumentRequest;
import com.logbook.backend.logbookbe.domain.issue.controller.dto.createIssueRequest;
import com.logbook.backend.logbookbe.domain.issue.controller.dto.deleteIssueResponse;
import com.logbook.backend.logbookbe.domain.issue.controller.dto.getAllIssuesRequest;
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
    public ResponseEntity<List<getAllIssuesRequest>> getAllIssues(@PathVariable UUID projectId) {
        List<getAllIssuesRequest> getAllIssues = issueService.getAllIssues(projectId);
        return ResponseEntity.ok(getAllIssues);
    }

    @GetMapping("/{issueId}")
    @Operation(summary = "특정 이슈 상세 조회", description = "특정 이슈의 상세 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Issue.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public Issue getIssueById(@PathVariable UUID issueId) {
        return issueService.getIssueById(issueId);
    }

    @PostMapping
    @Operation(summary = "새로운 이슈 생성", description = "새로운 이슈를 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = Issue.class))),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public UUID createIssue(@RequestBody createIssueRequest issueDTO, @PathVariable UUID projectId) {
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
    public Issue updateIssue(@PathVariable UUID issueId, @RequestBody Issue updatedIssue) {
        return issueService.updateIssue(issueId, updatedIssue);
    }

    @DeleteMapping("/{issueId}")
    @Operation(summary = "특정 이슈 삭제", description = "특정 이슈를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Issue.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public deleteIssueResponse deleteIssue(@PathVariable UUID issueId) {
        return issueService.deleteIssue(issueId);
    }

    @GetMapping("/filter")
    @Operation(summary = "이슈 필터링", description = "담당자와 상태를 기준으로 이슈를 필터링합니다.")@ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Issue.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public List<Issue> filterIssues(
            @PathVariable UUID projectId,
            @RequestParam(required = false) UUID assignee,
            @RequestParam(required = false) Status status) {
        return issueService.filterIssues(projectId, assignee, status);
    }

}
