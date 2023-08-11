package com.logbook.backend.logbookbe.releaseContent.service;

import com.logbook.backend.logbookbe.domain.document.model.Document;
import com.logbook.backend.logbookbe.domain.document.repository.DocumentRepository;
import com.logbook.backend.logbookbe.domain.member.model.Member;
import com.logbook.backend.logbookbe.domain.project.model.Project;
import com.logbook.backend.logbookbe.domain.releaseNote.controller.dto.CreateReleaseNoteRequest;
import com.logbook.backend.logbookbe.domain.releaseNote.controller.dto.CreatorRequest;
import com.logbook.backend.logbookbe.domain.releaseNote.controller.dto.DeleteReleaseNoteResponse;
import com.logbook.backend.logbookbe.domain.releaseNote.controller.dto.GetReleaseNoteResponse;
import com.logbook.backend.logbookbe.domain.releaseNote.model.ReleaseNote;
import com.logbook.backend.logbookbe.domain.releaseNote.repository.ReleaseNoteRepository;
import com.logbook.backend.logbookbe.domain.user.model.User;
import com.logbook.backend.logbookbe.releaseContent.controller.dto.CreateReleaseContentRequest;
import com.logbook.backend.logbookbe.releaseContent.controller.dto.DeleteReleaseContentResponse;
import com.logbook.backend.logbookbe.releaseContent.controller.dto.DocumentRequest;
import com.logbook.backend.logbookbe.releaseContent.controller.dto.GetReleaseContentResponse;
import com.logbook.backend.logbookbe.releaseContent.model.ReleaseContent;
import com.logbook.backend.logbookbe.releaseContent.repository.ReleaseContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReleaseContentService {

    @Autowired
    private ReleaseContentRepository releaseContentRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private ReleaseNoteRepository releaseNoteRepository;


    public List<GetReleaseContentResponse> getAllReleaseContents(UUID releaseNoteId) {

        List<ReleaseContent> releaseContents = releaseContentRepository.findByReleaseNoteReleaseNoteId(releaseNoteId);
        List<GetReleaseContentResponse> releaseContentDTOs = new ArrayList<>();

        for (ReleaseContent releaseContent : releaseContents) {
            GetReleaseContentResponse releaseContentDTO = new GetReleaseContentResponse();
            releaseContentDTO.setReleaseContentId(releaseContent.getReleaseContentId());

            Document document = releaseContent.getDocument();
            DocumentRequest documentDTO = new DocumentRequest();
            documentDTO.setDocumentId(document.getDocumentId());
            documentDTO.setDocumentTitle(document.getDocumentTitle());

            releaseContentDTO.setDocument(documentDTO);

            releaseContentDTO.setReleaseSummary(releaseContent.getReleaseSummary());
            releaseContentDTO.setCategory(releaseContent.getCategory());
            releaseContentDTO.setDocumentLink(releaseContent.getDocumentLink());

            releaseContentDTOs.add(releaseContentDTO);
        }

        return releaseContentDTOs;
    }

    public GetReleaseContentResponse getReleaseContentById(UUID releaseContentId){
        ReleaseContent releaseContent = releaseContentRepository.findByReleaseContentId(releaseContentId);

        GetReleaseContentResponse releaseContentDTO = new GetReleaseContentResponse();

        releaseContentDTO.setReleaseContentId(releaseContent.getReleaseContentId());

        Document document = releaseContent.getDocument();
        DocumentRequest documentDTO = new DocumentRequest();
        documentDTO.setDocumentId(document.getDocumentId());
        documentDTO.setDocumentTitle(document.getDocumentTitle());

        releaseContentDTO.setDocument(documentDTO);

        releaseContentDTO.setReleaseSummary(releaseContentDTO.getReleaseSummary());
        releaseContentDTO.setCategory(releaseContent.getCategory());
        releaseContentDTO.setDocumentLink(releaseContentDTO.getDocumentLink());

        return releaseContentDTO;

    }


    public UUID createReleaseContent(CreateReleaseContentRequest releaseContentDTO, UUID releaseNoteId){
        ReleaseContent releaseContent = new ReleaseContent();
        releaseContent.setReleaseSummary(releaseContentDTO.getReleaseSummary());

        Document document = documentRepository.findByDocumentId(releaseContentDTO.getDocument().getDocumentId());
        releaseContent.setDocument(document);

        releaseContent.setCategory(releaseContentDTO.getCategory());
        releaseContent.setDocumentLink(releaseContentDTO.getDocumentLink());



        ReleaseNote releaseNote = releaseNoteRepository.findByReleaseNoteId(releaseNoteId);
        if (releaseNote != null) {
            releaseContent.setReleaseNote(releaseNote);
        } else {
            throw new RuntimeException("해당하는 릴리즈노트가 없습니다.");
        }

        ReleaseContent savedReleaseContent = releaseContentRepository.save(releaseContent);

        return savedReleaseContent.getReleaseContentId();

    }

    public CreateReleaseContentRequest updateReleaseContent(UUID releaseContentId, CreateReleaseContentRequest updatedReleaseContentDTO) {
        Optional<ReleaseContent> existingReleaseContentOptional = releaseContentRepository.findById(releaseContentId);

        if (existingReleaseContentOptional.isPresent()) {
            ReleaseContent existingReleaseContent = existingReleaseContentOptional.get();

            Document document = documentRepository.findByDocumentId(updatedReleaseContentDTO.getDocument().getDocumentId());
            existingReleaseContent.setDocument(document);

            existingReleaseContent.setReleaseSummary(updatedReleaseContentDTO.getReleaseSummary());
            existingReleaseContent.setCategory(updatedReleaseContentDTO.getCategory());
            existingReleaseContent.setDocumentLink(updatedReleaseContentDTO.getDocumentLink());

            ReleaseContent updatedReleaseContent = releaseContentRepository.save(existingReleaseContent);


            CreateReleaseContentRequest responseDTO = new CreateReleaseContentRequest();

            DocumentRequest documentRequest = new DocumentRequest();
            documentRequest.setDocumentId(document.getDocumentId());
            documentRequest.setDocumentTitle(document.getDocumentTitle());
            responseDTO.setDocument(documentRequest);

            responseDTO.setReleaseSummary(updatedReleaseContentDTO.getReleaseSummary());
            responseDTO.setCategory(updatedReleaseContentDTO.getCategory());
            responseDTO.setDocumentLink(updatedReleaseContentDTO.getDocumentLink());

            return responseDTO;
        } else {
            throw new NotFoundException("해당하는 릴리즈 콘텐츠가 없습니다.");
        }

    }

    public DeleteReleaseContentResponse deleteReleaseContent(UUID releaseContentId) {
        releaseContentRepository.deleteById(releaseContentId);
        return new DeleteReleaseContentResponse(releaseContentId, "릴리즈 콘텐츠가 성공적으로 삭제되었습니다.");
    }



}
