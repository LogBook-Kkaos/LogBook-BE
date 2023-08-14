package com.logbook.backend.logbookbe.domain.document.controller;

import com.logbook.backend.logbookbe.domain.document.dto.createDocumentFilesRequest;
import com.logbook.backend.logbookbe.domain.document.dto.createDocumentRequest;
import com.logbook.backend.logbookbe.domain.document.dto.getAllDocumentRequest;
import com.logbook.backend.logbookbe.domain.document.dto.getDocumentRequest;
import com.logbook.backend.logbookbe.domain.document.service.DocumentService;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public boolean createDocument(@RequestBody createDocumentRequest request, @PathVariable UUID projectId) {
        List<String> imageUrlList = request.getImageUrlList();
        UUID documentId = documentService.createDocument(request, projectId);
        boolean documentFiles = documentService.createDocumentFiles(imageUrlList, documentId);

        return documentFiles;
    }


    @Operation(summary = "모든 기술문서 조회", description = "모든 기술문서 목록을 가져옵니다.")
    @GetMapping
    public ResponseEntity<List<getAllDocumentRequest>> getAllDocument(@PathVariable UUID projectId) {
        List<getAllDocumentRequest> getAllDocuments = documentService.getAllDocuments(projectId);
        System.out.println(getAllDocuments);
        return ResponseEntity.ok(getAllDocuments);
    }

    @Operation(summary = "특정 기술문서 조회", description = "특정 기술문서를 확인합니다.")
    @GetMapping("/{documentId}")
    public ResponseEntity<getDocumentRequest> getDocument(@PathVariable UUID projectId, @PathVariable UUID documentId) {
        getDocumentRequest getDocument = documentService.getDocument(documentId);
        return new ResponseEntity<getDocumentRequest>(getDocument, HttpStatus.CREATED);
    }

    @Operation(summary = "기술문서 삭제", description = "특정 기술문서를 삭제합니다.")
    @DeleteMapping("/{documentId}")
    public ResponseEntity<String> deleteDocument(@PathVariable UUID projectId, @PathVariable UUID documentId) {
        boolean deleted = documentService.deleteDocument(documentId);

        if (deleted) {
            return ResponseEntity.ok("Document deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete document");
        }
    }

    @Operation(summary = "기술문서 수정", description = "기술문서를 수정합니다.")
    @PutMapping("/{documentId}")
    public boolean updateDocument(@RequestBody createDocumentRequest request, @PathVariable UUID projectId, @PathVariable UUID documentId) {
        List<String> imageUrlList = request.getImageUrlList();
        boolean updated = documentService.updateDocument(documentId, request);
        boolean documentFiles = documentService.updateDocumentFiles(documentId, imageUrlList);

        return updated&&documentFiles;
    }

}
