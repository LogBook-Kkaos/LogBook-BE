package com.logbook.backend.logbookbe.domain.issue.controller;

import com.logbook.backend.logbookbe.domain.issue.controller.dto.IssueDeleteResponse;
import com.logbook.backend.logbookbe.domain.issue.model.Issue;
import com.logbook.backend.logbookbe.domain.issue.service.IssueService;
import com.logbook.backend.logbookbe.domain.project.model.Project;
import com.logbook.backend.logbookbe.global.error.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<Issue> getAllIssues(@PathVariable Integer projectId) {
        return issueService.getAllIssues(projectId);
    }

    @GetMapping("/{issueId}")
    @Operation(summary = "특정 이슈 상세 조회", description = "특정 이슈의 상세 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Issue.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public Issue getIssueById(@PathVariable Integer issueId) {
        return issueService.getIssueById(issueId);
    }

    @PostMapping
    @Operation(summary = "새로운 이슈 생성", description = "새로운 이슈를 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = Issue.class))),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public Issue createIssue(@PathVariable Integer projectId, @RequestBody Issue issue) {
        return issueService.createIssue(projectId, issue);
    }

    @PutMapping("/{issueId}")
    @Operation(summary = "특정 이슈 정보 업데이트", description = "특정 이슈의 정보를 업데이트합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Issue.class))),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public Issue updateIssue(@PathVariable Integer issueId, @RequestBody Issue updatedIssue) {
        return issueService.updateIssue(issueId, updatedIssue);
    }

    @DeleteMapping("/{issueId}")
    @Operation(summary = "특정 이슈 삭제", description = "특정 이슈를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Issue.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public IssueDeleteResponse deleteIssue(@PathVariable Integer issueId) {
        return issueService.deleteIssue(issueId);
    }

    @GetMapping("/filter")
    @Operation(summary = "이슈 필터링", description = "이슈를 필터링합니다.")@ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Issue.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public List<Issue> filterIssues(
            @PathVariable Integer projectId,
            @RequestParam(required = false) Integer assignee,
            @RequestParam(required = false) String status) {
        return issueService.filterIssues(projectId, assignee, status);
    }

}
