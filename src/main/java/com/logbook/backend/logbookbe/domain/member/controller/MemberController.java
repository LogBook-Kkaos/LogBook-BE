package com.logbook.backend.logbookbe.domain.member.controller;

import com.logbook.backend.logbookbe.domain.member.controller.dto.MemberRequest;
import com.logbook.backend.logbookbe.domain.member.controller.dto.EditMemberResponse;
import com.logbook.backend.logbookbe.domain.member.controller.dto.MemberInfoResponse;
import com.logbook.backend.logbookbe.domain.member.controller.dto.MemberResponse;
import com.logbook.backend.logbookbe.domain.member.model.Member;
import com.logbook.backend.logbookbe.domain.member.repository.MemberRepository;
import com.logbook.backend.logbookbe.domain.member.service.MemberService;
import com.logbook.backend.logbookbe.domain.project.model.Project;
import com.logbook.backend.logbookbe.domain.project.repository.ProjectRepository;
import com.logbook.backend.logbookbe.domain.project.service.ProjectService;
import com.logbook.backend.logbookbe.domain.user.model.User;
import com.logbook.backend.logbookbe.domain.user.repository.UserRepository;
import com.logbook.backend.logbookbe.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.logbook.backend.logbookbe.global.error.ErrorResponse;
import com.logbook.backend.logbookbe.domain.member.exception.MemberNotFoundException;
import com.logbook.backend.logbookbe.domain.project.exception.ProjectNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects/{project_id}/members")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    private final MemberRepository memberRepository;
    private ProjectRepository projectRepository;
    private UserRepository userRepository;

    @Autowired
    public MemberController(MemberService memberService, MemberRepository memberRepository,
                            ProjectRepository projectRepository, UserRepository userRepository) {
        this.memberService = memberService;
        this.memberRepository = memberRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Operation(summary = "멤버 생성", description = "멤버를 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Member.class))),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<MemberResponse> createMember(@PathVariable("project_id") UUID projectId,
                                                       @RequestBody List<MemberRequest> requests) {
        Project project = projectService.getProjectById(projectId);

        for (MemberRequest request : requests) {
            User user = userService.findUserByEmail(request.getEmail());
            Member existingMember = memberRepository.findByUserAndProject(user,project);

            if (existingMember != null) {
                return ResponseEntity.badRequest().body(new MemberResponse(UUID.randomUUID(), "Member already exists for this user and project."));
            } else {
                Member member = Member.createMember(request.getPermissionLevel(), project, user);
                memberService.createMember(member);
            }
        }
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "멤버 전체 조회", description = "특정 프로젝트의 모든 멤버를 가져옵니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Member.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<List<MemberInfoResponse>> getMembersForProject(@PathVariable("project_id") UUID projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(ProjectNotFoundException::new);

        List<User> members = memberRepository.findUsersByProjectId(projectId);

        List<MemberInfoResponse> responses = new ArrayList<>();
        for (User user : members) {
            MemberInfoResponse memberInfoResponse = new MemberInfoResponse();
            memberInfoResponse.setUserName(user.getUserName());
            memberInfoResponse.setEmail(user.getEmail());
            Member member = memberRepository.findByUserAndProject(user,project);
            memberInfoResponse.setPermissionLevel(member.getPermissionLevel());
            responses.add(memberInfoResponse);
        }

        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "멤버 상세 가져오기", description = "특정 멤버의 상세 정보를 가져옵니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Member.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{memberId}")
    public ResponseEntity<EditMemberResponse> getMembers(@PathVariable("project_id") UUID projectId,
                                                        @PathVariable UUID memberId) {
        return memberRepository.findById(memberId)
                .map(existingMember -> {
                    EditMemberResponse editMemberResponse = new EditMemberResponse();
                    editMemberResponse.setMemberId(existingMember.getMemberId());
                    editMemberResponse.setPermissionLevel(existingMember.getPermissionLevel());
                    return ResponseEntity.ok(editMemberResponse);
                })
                .orElseThrow(MemberNotFoundException::new);
    }

    @Operation(summary = "멤버 권한 확인", description = "특정 멤버의 권한을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Member.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/permission")
    public MemberInfoResponse getMember(@PathVariable("project_id") UUID projectId, @RequestParam("email") String email) {
        User user = userService.findUserByEmail(email);
        Project project = projectService.getProjectById(projectId);
        Member member = memberRepository.findByUserAndProject(user, project);
        MemberInfoResponse response = new MemberInfoResponse();
        response.setUserName(user.getUserName());
        response.setEmail(user.getEmail());
        response.setPermissionLevel(member.getPermissionLevel());

        return response;
    }

    @Operation(summary = "멤버 ID 확인", description = "특정 멤버의 ID를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Member.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/info")
    public UUID getMemberId(@PathVariable("project_id") UUID projectId, @RequestParam("email") String email) {
        User user = userService.findUserByEmail(email);
        Project project = projectService.getProjectById(projectId);
        Member member = memberRepository.findByUserAndProject(user, project);
        return member.getMemberId();
    }
    
    @Operation(summary = "멤버 업데이트", description = "기존 멤버 정보를 업데이트합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Member.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/edit")
    public ResponseEntity<List<MemberResponse>> updateMember(@PathVariable("project_id") UUID projectId,
                                                       @RequestBody List<MemberRequest> updatedMembers) {
        List<MemberResponse> updated = memberService.updateMembers(projectId, updatedMembers);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "멤버 삭제", description = "특정 멤버를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Object.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/delete")
    public ResponseEntity<List<MemberResponse>> deleteMember(@PathVariable("project_id") UUID projectId,
                                                              @RequestBody List<MemberRequest> deletedMembers) {
        List<MemberResponse> deleted = memberService.deleteMembers(projectId, deletedMembers);
        return ResponseEntity.ok(deleted);
    }
}
