package com.logbook.backend.logbookbe.domain.releaseNote.service;

import com.logbook.backend.logbookbe.domain.member.model.Member;
import com.logbook.backend.logbookbe.domain.member.repository.MemberRepository;
import com.logbook.backend.logbookbe.domain.member.service.MemberService;
import com.logbook.backend.logbookbe.domain.project.model.Project;
import com.logbook.backend.logbookbe.domain.project.repository.ProjectRepository;
import com.logbook.backend.logbookbe.domain.releaseNote.controller.dto.CreateReleaseNoteRequest;
import com.logbook.backend.logbookbe.domain.releaseNote.controller.dto.CreatorRequest;
import com.logbook.backend.logbookbe.domain.releaseNote.controller.dto.GetReleaseNoteResponse;
import com.logbook.backend.logbookbe.domain.releaseNote.model.ReleaseNote;
import com.logbook.backend.logbookbe.domain.releaseNote.repository.ReleaseNoteRepository;
import com.logbook.backend.logbookbe.domain.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReleaseNoteServiceTest {

    @InjectMocks
    private ReleaseNoteService releaseNoteService;

    @Mock
    private ReleaseNoteRepository releaseNoteRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MemberService memberService;


    private ReleaseNote releaseNote;
    private Project project;
    private Member member;
    private User user;
    private CreateReleaseNoteRequest createReleaseNoteRequest;
    private UUID projectId;

    @BeforeEach
    public void setUp() {
        projectId = UUID.randomUUID();
        project = new Project();
        project.setProjectId(projectId);

        user = new User();
        user.setUserId(UUID.randomUUID());
        user.setUserName("test_user");

        member = new Member();
        member.setMemberId(UUID.randomUUID());
        member.setUser(user);


        releaseNote = new ReleaseNote();
        releaseNote.setReleaseNoteId(UUID.randomUUID());
        releaseNote.setProject(project);
        releaseNote.setCreator(member);
        releaseNote.setReleaseTitle("Release Note Test");
        releaseNote.setCreationDate(Timestamp.from(Instant.now()));
        releaseNote.setVersion("v 1.1.1");
        releaseNote.setImportant(false);
        releaseNote.setPublic(true);

        createReleaseNoteRequest = new CreateReleaseNoteRequest();
        CreatorRequest creatorRequest = new CreatorRequest();
        creatorRequest.setCreatorId(member.getMemberId());
        createReleaseNoteRequest.setCreator(creatorRequest);
        createReleaseNoteRequest.setReleaseTitle("Release Note Test");
        createReleaseNoteRequest.setVersion("v 1.1.1");
        createReleaseNoteRequest.setImportant(false);
        createReleaseNoteRequest.setPublic(true);



    }

    @Test
    public void getAllReleaseNotesTest() {
        when(releaseNoteRepository.findByProjectProjectId(projectId)).thenReturn(Arrays.asList(releaseNote));

        List<GetReleaseNoteResponse> result = releaseNoteService.getAllReleaseNotes(projectId);

        assertEquals(1, result.size());
        assertEquals(releaseNote.getReleaseNoteId(), result.get(0).getReleaseNoteId());
        assertEquals(releaseNote.getReleaseTitle(), result.get(0).getReleaseTitle());
        assertEquals(releaseNote.getVersion(), result.get(0).getVersion());
        assertEquals(releaseNote.isImportant(), result.get(0).isImportant());
        assertEquals(releaseNote.isPublic(), result.get(0).isPublic());

    }


    @Test
    public void getReleaseNoteById() {
        when(releaseNoteRepository.findByReleaseNoteId(releaseNote.getReleaseNoteId())).thenReturn(releaseNote);

        GetReleaseNoteResponse result = releaseNoteService.getReleaseNoteById(releaseNote.getReleaseNoteId());

        assertEquals(releaseNote.getReleaseNoteId(), result.getReleaseNoteId());
        assertEquals(releaseNote.getReleaseTitle(), result.getReleaseTitle());
        assertEquals(releaseNote.getVersion(), result.getVersion());
        assertEquals(releaseNote.isImportant(), result.isImportant());
        assertEquals(releaseNote.isPublic(), result.isPublic());
    }


    @Test
    public void createReleaseNoteTest() {
        when(memberService.findCreatorById(member.getMemberId())).thenReturn(member);
        when(projectRepository.findByProjectId(projectId)).thenReturn(Optional.of(project));
        when(releaseNoteRepository.save(any(ReleaseNote.class))).thenReturn(releaseNote);

        UUID createReleaseNoteId = releaseNoteService.createReleaseNote(createReleaseNoteRequest, projectId);

        assertEquals(releaseNote.getReleaseNoteId(), createReleaseNoteId);

    }

    @Test
    public void updateReleaseNoteTest() {
        when(releaseNoteRepository.findById(releaseNote.getReleaseNoteId())).thenReturn(Optional.of(releaseNote));
        when(memberService.findCreatorById(any(UUID.class))).thenReturn(member);
        when(releaseNoteRepository.save(any(ReleaseNote.class))).thenReturn(releaseNote);

        CreateReleaseNoteRequest updatedCreateReleaseNoteRequest = new CreateReleaseNoteRequest();
        updatedCreateReleaseNoteRequest.setReleaseTitle("Updated Release Title");
        updatedCreateReleaseNoteRequest.setVersion("v 2.0.0");

        CreatorRequest creatorRequest = new CreatorRequest();
        creatorRequest.setCreatorId(UUID.randomUUID());

        updatedCreateReleaseNoteRequest.setCreator(creatorRequest);

        CreateReleaseNoteRequest result = releaseNoteService.updateReleaseNote(releaseNote.getReleaseNoteId(), updatedCreateReleaseNoteRequest);

        assertEquals("Updated Release Title", result.getReleaseTitle());
        assertEquals("v 2.0.0", result.getVersion());
    }

    @Test
    public void deleteReleaseNoteTest() {
        UUID releaseNoteIdToDelete = UUID.randomUUID();
        releaseNoteService.deleteReleaseNote(releaseNoteIdToDelete);

        verify(releaseNoteRepository, times(1)).deleteById(releaseNoteIdToDelete);
    }




}
