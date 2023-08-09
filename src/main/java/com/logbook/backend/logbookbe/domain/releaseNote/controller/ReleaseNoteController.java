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

    @GetMapping
    public ResponseEntity<List<GetReleaseNoteResponse>> getAllReleaseNote(@PathVariable UUID projectId) {
        List<GetReleaseNoteResponse> getAllReleaseNotes = releaseNoteService.getAllReleaseNotes(projectId);
        return ResponseEntity.ok(getAllReleaseNotes);
    }

    @GetMapping("/{releaseNoteId}")
    public ResponseEntity<GetReleaseNoteResponse> getReleaseNoteById(@PathVariable UUID projectId, @PathVariable UUID releaseNoteId) {
        GetReleaseNoteResponse getReleaseNoteById = releaseNoteService.getReleaseNoteById(releaseNoteId);
        return new ResponseEntity<>(getReleaseNoteById, HttpStatus.CREATED);
    }

    @PostMapping
    public UUID createReleaseNote(@RequestBody CreateReleaseNoteRequest releaseNoteDTO, @PathVariable UUID projectId) {
        UUID CreateReleaseNoteRequest = releaseNoteService.createReleaseNote(releaseNoteDTO, projectId);
        return CreateReleaseNoteRequest;
    }

    @PutMapping("/{releaseNoteId}")
    public ResponseEntity<CreateReleaseNoteRequest> updateReleaseNote(@PathVariable("releaseNoteId") UUID releaseNoteId,
                                                         @Valid @RequestBody CreateReleaseNoteRequest updatedReleaseNote) {
        CreateReleaseNoteRequest updatedReleaseNoteResult = releaseNoteService.updateReleaseNote(releaseNoteId, updatedReleaseNote);
        return new ResponseEntity<>(updatedReleaseNoteResult, HttpStatus.OK);
    }

    @DeleteMapping("/{releaseNoteId}")
    public DeleteReleaseNoteResponse deleteReleaseNote(@PathVariable UUID releaseNoteId) {
        return releaseNoteService.deleteReleaseNote(releaseNoteId);
    }


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