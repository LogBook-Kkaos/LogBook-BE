package com.logbook.backend.logbookbe.domain.releaseNote.controller;

import com.logbook.backend.logbookbe.domain.document.dto.createDocumentRequest;
import com.logbook.backend.logbookbe.domain.document.dto.getAllDocumentRequest;
import com.logbook.backend.logbookbe.domain.document.dto.getDocumentRequest;
import com.logbook.backend.logbookbe.domain.issue.model.Issue;
import com.logbook.backend.logbookbe.domain.releaseNote.controller.dto.CreateReleaseNoteRequest;
import com.logbook.backend.logbookbe.domain.releaseNote.controller.dto.DeleteReleaseNoteResponse;
import com.logbook.backend.logbookbe.domain.releaseNote.controller.dto.GetReleaseNoteResponse;
import com.logbook.backend.logbookbe.domain.releaseNote.model.ReleaseNote;
import com.logbook.backend.logbookbe.domain.releaseNote.service.ReleaseNoteService;
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

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects/{projectId}/release_notes")
public class ReleaseNoteController {

    @Autowired
    private ReleaseNoteService releaseNoteService;

    @Operation(summary = "모든 릴리즈 노트 조회", description = "모든 릴리즈 노트를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GetReleaseNoteResponse.class))}),
    })
    @GetMapping
    public ResponseEntity<List<GetReleaseNoteResponse>> getAllReleaseNote(@PathVariable UUID projectId) {
        List<GetReleaseNoteResponse> getAllReleaseNotes = releaseNoteService.getAllReleaseNotes(projectId);
        return ResponseEntity.ok(getAllReleaseNotes);
    }

    @Operation(summary = "특정 릴리즈 노트 조회", description = "릴리즈 노트 아이디로 특정 릴리즈 노트를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GetReleaseNoteResponse.class))}),
    })
    @GetMapping("/{releaseNoteId}")
    public ResponseEntity<GetReleaseNoteResponse> getReleaseNoteById(@PathVariable UUID projectId, @PathVariable UUID releaseNoteId) {
        GetReleaseNoteResponse getReleaseNoteById = releaseNoteService.getReleaseNoteById(releaseNoteId);
        return new ResponseEntity<>(getReleaseNoteById, HttpStatus.CREATED);
    }

    @Operation(summary = "릴리즈 노트 생성", description = "릴리즈 노트를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UUID.class))}),
    })
    @PostMapping
    public UUID createReleaseNote(@RequestBody CreateReleaseNoteRequest releaseNoteDTO, @PathVariable UUID projectId) {
        UUID CreateReleaseNoteRequest = releaseNoteService.createReleaseNote(releaseNoteDTO, projectId);
        return CreateReleaseNoteRequest;
    }

    @Operation(summary = "릴리즈 노트 업데이트", description = "릴리즈 노트를 업데이트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CreateReleaseNoteRequest.class))}),
    })
    @PutMapping("/{releaseNoteId}")
    public ResponseEntity<CreateReleaseNoteRequest> updateReleaseNote(@PathVariable("releaseNoteId") UUID releaseNoteId,
                                                         @Valid @RequestBody CreateReleaseNoteRequest updatedReleaseNote) {
        CreateReleaseNoteRequest updatedReleaseNoteResult = releaseNoteService.updateReleaseNote(releaseNoteId, updatedReleaseNote);
        return new ResponseEntity<>(updatedReleaseNoteResult, HttpStatus.OK);
    }

    @Operation(summary = "릴리즈 노트 삭제", description = "릴리즈 노트를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DeleteReleaseNoteResponse.class))}),
    })
    @DeleteMapping("/{releaseNoteId}")
    public DeleteReleaseNoteResponse deleteReleaseNote(@PathVariable UUID releaseNoteId) {
        return releaseNoteService.deleteReleaseNote(releaseNoteId);
    }

    // ** TODO ** //

//    @GetMapping("/search")
//    public List<ReleaseNote> searchReleaseNotes(/* 검색 파라미터 */) {
//        return releaseNoteService.searchReleaseNotes(/* 검색 파라미터 전달 */);
//    }
//
//    @GetMapping("/filter")
//    public List<ReleaseNote> filterReleaseNotes(/* 필터링 파라미터 */) {
//        return releaseNoteService.filterReleaseNotes(/* 필터링 파라미터 전달 */);
//    }


}