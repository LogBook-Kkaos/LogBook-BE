package com.logbook.backend.logbookbe.domain.releaseNote.service;

import com.logbook.backend.logbookbe.domain.member.model.Member;
import com.logbook.backend.logbookbe.domain.member.service.MemberService;
import com.logbook.backend.logbookbe.domain.project.model.Project;
import com.logbook.backend.logbookbe.domain.project.repository.ProjectRepository;
import com.logbook.backend.logbookbe.domain.releaseNote.controller.dto.*;
import com.logbook.backend.logbookbe.domain.releaseNote.model.ReleaseNote;
import com.logbook.backend.logbookbe.domain.releaseNote.repository.ReleaseNoteRepository;
import com.logbook.backend.logbookbe.domain.user.model.User;
import com.logbook.backend.logbookbe.releaseContent.model.ReleaseContent;
import com.logbook.backend.logbookbe.releaseContent.repository.ReleaseContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Service
public class ReleaseNoteService {

    @Autowired
    private ReleaseNoteRepository releaseNoteRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ReleaseContentRepository releaseContentRepository;

    public List<GetReleaseNoteResponse> getAllReleaseNotes(UUID projectId) {
        List<ReleaseNote> releaseNotes = releaseNoteRepository.findByProjectProjectId(projectId);
        List<GetReleaseNoteResponse> releaseNoteDTOs = new ArrayList<>();

        for (ReleaseNote releaseNote : releaseNotes) {
            GetReleaseNoteResponse releaseNoteDTO = createReleaseNoteDTO(releaseNote);

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

        List<ReleaseContent> releaseContents = releaseContentRepository.findByReleaseNoteReleaseNoteId(releaseNote.getReleaseNoteId());
        List<ReleaseContentRequest> releaseContentDTOs = new ArrayList<>();

        for (ReleaseContent releaseContent : releaseContents) {
            ReleaseContentRequest releaseContentDTO = new ReleaseContentRequest();
            releaseContentDTO.setReleaseSummary(releaseContent.getReleaseSummary());
            releaseContentDTO.setCategory(releaseContent.getCategory());
            releaseContentDTO.setDocumentLink(releaseContent.getDocumentLink());

            releaseContentDTOs.add(releaseContentDTO);
        }

        releaseNoteDTO.setReleaseContents(releaseContentDTOs);

        return releaseNoteDTO;

    }

    public UUID createReleaseNote(CreateReleaseNoteRequest releaseNoteDTO, UUID projectId){
        ReleaseNote releaseNote = new ReleaseNote();
        releaseNote.setReleaseTitle(releaseNoteDTO.getReleaseTitle());

        Member creator = memberService.findCreatorById(releaseNoteDTO.getCreator().getCreatorId());
        releaseNote.setCreator(creator);

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

        ReleaseNote savedReleaseNote = releaseNoteRepository.save(releaseNote);

        for (ReleaseContentRequest contentRequest : releaseNoteDTO.getReleaseContents()) {
            ReleaseContent content = new ReleaseContent();
            content.setReleaseSummary(contentRequest.getReleaseSummary());
            content.setCategory(contentRequest.getCategory());
            content.setDocumentLink(contentRequest.getDocumentLink());
            content.setReleaseNote(savedReleaseNote);
            releaseContentRepository.save(content);
        }

        return savedReleaseNote.getReleaseNoteId();

    }

    public CreateReleaseNoteRequest updateReleaseNote(UUID releaseNoteId, CreateReleaseNoteRequest updatedReleaseNoteDTO) {
        Optional<ReleaseNote> existingReleaseNoteOptional = releaseNoteRepository.findById(releaseNoteId);

        if (existingReleaseNoteOptional.isPresent()) {
            ReleaseNote existingReleaseNote = existingReleaseNoteOptional.get();

            Member creator = memberService.findCreatorById(updatedReleaseNoteDTO.getCreator().getCreatorId());
            existingReleaseNote.setCreator(creator);


            existingReleaseNote.setReleaseTitle(updatedReleaseNoteDTO.getReleaseTitle());
            existingReleaseNote.setVersion(updatedReleaseNoteDTO.getVersion());
            existingReleaseNote.setImportant(updatedReleaseNoteDTO.isImportant());
            existingReleaseNote.setPublic(updatedReleaseNoteDTO.isPublic());
            existingReleaseNote.setCreationDate(Timestamp.from(Instant.now()));

            ReleaseNote updatedReleaseNote = releaseNoteRepository.save(existingReleaseNote);


            CreateReleaseNoteRequest responseDTO = new CreateReleaseNoteRequest();

            CreatorRequest creatorRequest = new CreatorRequest();
            creatorRequest.setCreatorId(creator.getMemberId());
            creatorRequest.setUserName(creator.getUser().getUserName());
            responseDTO.setCreator(creatorRequest);

            responseDTO.setReleaseTitle(updatedReleaseNote.getReleaseTitle());
            responseDTO.setVersion(updatedReleaseNote.getVersion());
            responseDTO.setImportant(updatedReleaseNote.isImportant());
            responseDTO.setPublic(updatedReleaseNote.isPublic());
            responseDTO.setCreationDate(Timestamp.from(Instant.now()));

            return responseDTO;
        } else {
            throw new NotFoundException("해당하는 릴리즈 노트가 없습니다.");
        }

    }

    public DeleteReleaseNoteResponse deleteReleaseNote(UUID releaseNoteId) {
        releaseNoteRepository.deleteById(releaseNoteId);
        return new DeleteReleaseNoteResponse(releaseNoteId, "이슈가 성공적으로 삭제되었습니다.");
    }

    public List<GetReleaseNoteResponse> searchReleaseNotes(UUID projectId, String keyword){
        List<ReleaseNote> releaseNotes = releaseNoteRepository.findByProjectProjectId((projectId));

        List<GetReleaseNoteResponse> searchedReleaseNotes = new ArrayList<>();

        for (ReleaseNote releaseNote : releaseNotes) {
            if(releaseNote.getReleaseTitle().contains(keyword)){
                GetReleaseNoteResponse releaseNoteDTO = createReleaseNoteDTO(releaseNote);
                searchedReleaseNotes.add(releaseNoteDTO);
            }else{
                Member creator = releaseNote.getCreator();
                String creatorUsername = creator.getUser().getUserName();
                if(creatorUsername.contains(keyword)){
                    GetReleaseNoteResponse releaseNoteDTO = createReleaseNoteDTO(releaseNote);
                    searchedReleaseNotes.add(releaseNoteDTO);
                }
            }
        }
        return searchedReleaseNotes;
    }

    private GetReleaseNoteResponse createReleaseNoteDTO(ReleaseNote releaseNote) {


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

        List<ReleaseContent> releaseContents = releaseContentRepository.findByReleaseNoteReleaseNoteId(releaseNote.getReleaseNoteId());
        List<ReleaseContentRequest> releaseContentDTOs = new ArrayList<>();

        for (ReleaseContent releaseContent : releaseContents) {
            ReleaseContentRequest releaseContentDTO = new ReleaseContentRequest();
            releaseContentDTO.setReleaseSummary(releaseContent.getReleaseSummary());
            releaseContentDTO.setCategory(releaseContent.getCategory());
            releaseContentDTO.setDocumentLink(releaseContent.getDocumentLink());

            releaseContentDTOs.add(releaseContentDTO);
        }


        releaseNoteDTO.setReleaseContents(releaseContentDTOs);



        return releaseNoteDTO;
    }

}
