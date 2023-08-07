package com.logbook.backend.logbookbe.domain.document.controller;

import com.logbook.backend.logbookbe.domain.document.dto.createDocumentRequest;
import com.logbook.backend.logbookbe.domain.document.dto.getAllDocumentRequest;
import com.logbook.backend.logbookbe.domain.document.dto.getDocumentRequest;
import com.logbook.backend.logbookbe.domain.document.service.DocumentService;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects/{projectId}/documents")
public class DocumentController {
    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
            this.documentService = documentService;
    }

    @Operation(summary = "기술문서 생성", description = "기술문서를 생성합니다.")
    @PostMapping
    public boolean createDocument(@RequestBody createDocumentRequest documentDTO, @PathVariable UUID projectId) {
        boolean createdDocumentRequest = documentService.createDocument(documentDTO, projectId);
        return createdDocumentRequest;
    }

    @Operation(summary = "모든 기술문서 조회", description = "모든 기술문서 목록을 가져옵니다.")
    @GetMapping
    public ResponseEntity<List<getAllDocumentRequest>> getAllDocument(@PathVariable UUID projectId) {
        List<getAllDocumentRequest> getAllDocuments = documentService.getAllDocuments(projectId);
        return ResponseEntity.ok(getAllDocuments);
    }

    @Operation(summary = "특정 기술문서 조회", description = "특정 기술문서를 확인합니다.")
    @GetMapping("/{documentId}")
    public ResponseEntity<getDocumentRequest> getDocument(@PathVariable UUID projectId, @PathVariable UUID documentId) {
        getDocumentRequest getDocument = documentService.getDocument(documentId);
        return new ResponseEntity<>(getDocument, HttpStatus.CREATED);
    }
}
