package com.logbook.backend.logbookbe.releaseContent.controller;

import com.logbook.backend.logbookbe.domain.releaseNote.controller.dto.CreateReleaseNoteRequest;
import com.logbook.backend.logbookbe.domain.releaseNote.controller.dto.DeleteReleaseNoteResponse;
import com.logbook.backend.logbookbe.domain.releaseNote.controller.dto.GetReleaseNoteResponse;
import com.logbook.backend.logbookbe.domain.releaseNote.service.ReleaseNoteService;
import com.logbook.backend.logbookbe.releaseContent.controller.dto.CreateReleaseContentRequest;
import com.logbook.backend.logbookbe.releaseContent.controller.dto.DeleteReleaseContentResponse;
import com.logbook.backend.logbookbe.releaseContent.controller.dto.GetReleaseContentResponse;
import com.logbook.backend.logbookbe.releaseContent.model.ReleaseContent;
import com.logbook.backend.logbookbe.releaseContent.repository.ReleaseContentRepository;
import com.logbook.backend.logbookbe.releaseContent.service.ReleaseContentService;
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
@RequestMapping("/api/projects/{projectId}/release_notes/{releaseNoteId}/release_contents")
public class ReleaseContentController {

    @Autowired
    private ReleaseContentService releaseContentService;

    @GetMapping
    public ResponseEntity<List<GetReleaseContentResponse>> getAllReleaseContent(@PathVariable("releaseNoteId") UUID releaseNoteId) {
        List<GetReleaseContentResponse> getAllReleaseContents = releaseContentService.getAllReleaseContents(releaseNoteId);
        return ResponseEntity.ok(getAllReleaseContents);
    }

    @GetMapping("/{releaseContentId}")
    public ResponseEntity<GetReleaseContentResponse> getReleaseContentById(@PathVariable UUID releaseNoteId, @PathVariable UUID releaseContentId) {
        GetReleaseContentResponse getReleaseContentById = releaseContentService.getReleaseContentById(releaseContentId);
        return new ResponseEntity<GetReleaseContentResponse>(getReleaseContentById, HttpStatus.CREATED);
    }

    @PostMapping
    public UUID createReleaseContent(@RequestBody CreateReleaseContentRequest releaseContentDTO, @PathVariable UUID releaseNoteId) {
        UUID CreateReleaseContentRequest = releaseContentService.createReleaseContent(releaseContentDTO, releaseNoteId);
        return CreateReleaseContentRequest;
    }

    @PutMapping("/{releaseContentId}")
    public ResponseEntity<CreateReleaseContentRequest> updateReleaseContent(@PathVariable("releaseContentId") UUID releaseContentId,
                                                                      @Valid @RequestBody CreateReleaseContentRequest updatedReleaseContent) {
        CreateReleaseContentRequest updatedReleaseContentResult = releaseContentService.updateReleaseContent(releaseContentId, updatedReleaseContent);
        return new ResponseEntity<CreateReleaseContentRequest>(updatedReleaseContentResult, HttpStatus.OK);
    }

    @DeleteMapping("/{releaseContentId}")
    public DeleteReleaseContentResponse deleteReleaseContent(@PathVariable UUID releaseContentId) {
        return releaseContentService.deleteReleaseContent(releaseContentId);
    }


}
