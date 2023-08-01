package com.logbook.backend.logbookbe.domain.member.controller;

import com.logbook.backend.logbookbe.domain.member.exception.MemberAlreadyExistsException;
import com.logbook.backend.logbookbe.domain.member.exception.MemberUpdateException;
import com.logbook.backend.logbookbe.domain.member.model.Member;
import com.logbook.backend.logbookbe.domain.member.repository.MemberRepository;
import com.logbook.backend.logbookbe.domain.member.service.MemberService;
import com.logbook.backend.logbookbe.domain.project.model.Project;
import com.logbook.backend.logbookbe.domain.project.repository.ProjectRepository;
import com.logbook.backend.logbookbe.domain.user.model.User;
import com.logbook.backend.logbookbe.domain.user.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.logbook.backend.logbookbe.domain.member.exception.MemberNotFoundException;
import com.logbook.backend.logbookbe.domain.user.exception.UserNotFoundException;
import com.logbook.backend.logbookbe.domain.project.exception.ProjectNotFoundException;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    @Autowired
    private MemberService memberService;
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
    
    @PostMapping
    public ResponseEntity<Member> createMember(@RequestBody Member member) {
        Project project = projectRepository.findById(member.getProject().getProjectId())
                .orElseThrow(ProjectNotFoundException::new);
        User user = userRepository.findById(member.getUser().getId())
                .orElseThrow(UserNotFoundException::new);

        if (memberRepository.existsByProjectAndUser(project, user)) {
            throw new MemberAlreadyExistsException();
        }

        member.setProject(project);
        member.setUser(user);

        Member savedMember = memberRepository.save(member);
        return ResponseEntity.ok(savedMember);
    }


    @GetMapping("/{memberId}")
    public ResponseEntity<Member> getMember(@PathVariable Long memberId) {
        return memberRepository.findById(memberId)
                .map(ResponseEntity::ok)
                .orElseThrow(MemberNotFoundException::new);
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<Member> updateMember(@PathVariable Long memberId, @RequestBody Member updatedMember) {
        return memberRepository.findById(memberId)
                .map(existingMember -> {
                    if (updatedMember.getPermissionLevel() != null) {
                        existingMember.setPermissionLevel(updatedMember.getPermissionLevel());
                    }
                    if (updatedMember.getRole() != null) {
                        existingMember.setRole(updatedMember.getRole());
                    }
                    if (updatedMember.getProject() != null) {
                        Project project = updatedMember.getProject();
                        if (project.getProjectId() != null) {
                            existingMember.setProject(project);
                        }
                    }
                    if (updatedMember.getUser() != null) {
                        User user = updatedMember.getUser();
                        if (user.getId() != null) {
                            existingMember.setUser(user);
                        }
                    }
                    return ResponseEntity.ok(memberRepository.save(existingMember));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Object> deleteMember(@PathVariable Long memberId) {
        Member existingMember = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        memberRepository.delete(existingMember);
        return ResponseEntity.ok().build();
    }
}
