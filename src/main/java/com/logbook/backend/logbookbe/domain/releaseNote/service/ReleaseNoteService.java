package com.logbook.backend.logbookbe.domain.releaseNote.service;

import com.logbook.backend.logbookbe.domain.document.dto.createDocumentRequest;
import com.logbook.backend.logbookbe.domain.document.dto.getAllDocumentRequest;
import com.logbook.backend.logbookbe.domain.document.dto.getDocumentRequest;
import com.logbook.backend.logbookbe.domain.document.model.Document;
import com.logbook.backend.logbookbe.domain.issue.model.Issue;
import com.logbook.backend.logbookbe.domain.issue.type.Status;
import com.logbook.backend.logbookbe.domain.member.model.Member;
import com.logbook.backend.logbookbe.domain.project.model.Project;
import com.logbook.backend.logbookbe.domain.project.repository.ProjectRepository;
import com.logbook.backend.logbookbe.domain.releaseNote.controller.dto.CreateReleaseNoteRequest;
import com.logbook.backend.logbookbe.domain.releaseNote.controller.dto.CreatorRequest;
import com.logbook.backend.logbookbe.domain.releaseNote.controller.dto.DeleteReleaseNoteResponse;
import com.logbook.backend.logbookbe.domain.releaseNote.controller.dto.GetReleaseNoteResponse;
import com.logbook.backend.logbookbe.domain.releaseNote.model.ReleaseNote;
import com.logbook.backend.logbookbe.domain.releaseNote.repository.ReleaseNoteRepository;
import com.logbook.backend.logbookbe.domain.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReleaseNoteService {

    @Autowired
    private ReleaseNoteRepository releaseNoteRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public List<GetReleaseNoteResponse> getAllReleaseNotes(UUID projectId) {
        List<ReleaseNote> releaseNotes = releaseNoteRepository.findByProjectProjectId(projectId);
        List<GetReleaseNoteResponse> releaseNoteDTOs = new ArrayList<>();

        for (ReleaseNote releaseNote : releaseNotes) {
            GetReleaseNoteResponse releaseNoteDTO = new GetReleaseNoteResponse();
            releaseNoteDTO.setReleaseNoteId(releaseNote.getReleaseNoteId());

            Member creator = releaseNote.getCreator();
            CreatorRequest creatorDTO = new CreatorRequest();
            creatorDTO.setCreatorId(creator.getMemberId());

            User user = creator.getUser();
            String username = user.getUserName();
            creatorDTO.setUserName(username);

            releaseNoteDTO.setCreator(creatorDTO);

            releaseNoteDTO.setReleaseTitle(releaseNote.getReleaseTitle());
            releaseNoteDTO.setVersion(releaseNote.getVersion());
            releaseNoteDTO.setCreationDate(releaseNote.getCreationDate());
            releaseNoteDTO.setImportant(releaseNote.isImportant());
            releaseNoteDTO.setPublic(releaseNote.isPublic());

            releaseNoteDTOs.add(releaseNoteDTO);
        }

        return releaseNoteDTOs;
    }

    public GetReleaseNoteResponse getReleaseNoteById(UUID releaseNoteId){
        ReleaseNote releaseNote = releaseNoteRepository.findByReleaseNoteId(releaseNoteId);
        GetReleaseNoteResponse releaseNoteDTO = new GetReleaseNoteResponse();

        releaseNoteDTO.setReleaseNoteId(releaseNote.getReleaseNoteId());

        Member creator = releaseNote.getCreator();
        CreatorRequest creatorDTO = new CreatorRequest();
        creatorDTO.setCreatorId(creator.getMemberId());

        User user = creator.getUser();
        String username = user.getUserName();
        creatorDTO.setUserName(username);

        releaseNoteDTO.setCreator(creatorDTO);

        releaseNoteDTO.setReleaseTitle(releaseNote.getReleaseTitle());
        releaseNoteDTO.setVersion(releaseNote.getVersion());
        releaseNoteDTO.setCreationDate(releaseNote.getCreationDate());
        releaseNoteDTO.setImportant(releaseNote.isImportant());
        releaseNoteDTO.setPublic(releaseNote.isPublic());

        return releaseNoteDTO;

    }

    public UUID createReleaseNote(CreateReleaseNoteRequest releaseNoteDTO, UUID projectId){
        ReleaseNote releaseNote = new ReleaseNote();
        releaseNote.setReleaseTitle(releaseNoteDTO.getReleaseTitle());
        releaseNote.setCreator(releaseNoteDTO.getCreator());
        releaseNote.setVersion(releaseNoteDTO.getVersion());
        releaseNote.setCreationDate(Timestamp.from(Instant.now()));
        releaseNote.setPublic(releaseNoteDTO.isPublic());
        releaseNote.setImportant(releaseNoteDTO.isImportant());

        Optional<Project> projectOptional = projectRepository.findByProjectId(projectId);
        if (projectOptional.isPresent()) {
            releaseNote.setProject(projectOptional.get());
        } else {
            throw new RuntimeException("해당하는 프로젝트가 없습니다.");
        }

        releaseNoteRepository.save(releaseNote);

        return releaseNote.getReleaseNoteId();

    }

    public CreateReleaseNoteRequest updateReleaseNote(UUID releaseNoteId, CreateReleaseNoteRequest updatedReleaseNoteDTO) {
        Optional<ReleaseNote> existingReleaseNoteOptional = releaseNoteRepository.findById(releaseNoteId);

        if (existingReleaseNoteOptional.isPresent()) {
            ReleaseNote existingReleaseNote = existingReleaseNoteOptional.get();
            existingReleaseNote.setCreator(updatedReleaseNoteDTO.getCreator());
            existingReleaseNote.setReleaseTitle(updatedReleaseNoteDTO.getReleaseTitle());
            existingReleaseNote.setVersion(updatedReleaseNoteDTO.getVersion());
            existingReleaseNote.setImportant(updatedReleaseNoteDTO.isImportant());
            existingReleaseNote.setPublic(updatedReleaseNoteDTO.isPublic());
            existingReleaseNote.setCreationDate(updatedReleaseNoteDTO.getCreationDate());

            ReleaseNote updatedReleaseNote = releaseNoteRepository.save(existingReleaseNote);


            CreateReleaseNoteRequest responseDTO = new CreateReleaseNoteRequest();
            responseDTO.setCreator(updatedReleaseNote.getCreator());
            responseDTO.setReleaseTitle(updatedReleaseNote.getReleaseTitle());
            responseDTO.setVersion(updatedReleaseNote.getVersion());
            responseDTO.setImportant(updatedReleaseNote.isImportant());
            responseDTO.setPublic(updatedReleaseNote.isPublic());
            responseDTO.setCreationDate(updatedReleaseNote.getCreationDate());

            return responseDTO;
        } else {
            throw new NotFoundException("해당하는 릴리즈 노트가 없습니다.");
        }

    }

    public DeleteReleaseNoteResponse deleteReleaseNote(UUID releaseNoteId) {
        releaseNoteRepository.deleteById(releaseNoteId);
        return new DeleteReleaseNoteResponse(releaseNoteId, "이슈가 성공적으로 삭제되었습니다.");
    }

}
