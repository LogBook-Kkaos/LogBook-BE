package com.logbook.backend.logbookbe.domain.member.service;

import com.logbook.backend.logbookbe.domain.member.controller.dto.MemberRequest;
import com.logbook.backend.logbookbe.domain.member.controller.dto.MemberResponse;
import com.logbook.backend.logbookbe.domain.member.model.Member;
import com.logbook.backend.logbookbe.domain.member.repository.MemberRepository;
import com.logbook.backend.logbookbe.domain.project.model.Project;
import com.logbook.backend.logbookbe.domain.project.repository.ProjectRepository;
import com.logbook.backend.logbookbe.domain.project.service.ProjectService;
import com.logbook.backend.logbookbe.domain.user.model.User;
import com.logbook.backend.logbookbe.domain.user.repository.UserRepository;
import com.logbook.backend.logbookbe.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    public List<Member> getAllUsers() {
        return memberRepository.findAll();
    }

    public Member findMemberById(UUID creatorId) {
        Optional<Member> memberOptional = memberRepository.findById(creatorId);
        return memberOptional.orElseThrow(() -> new NotFoundException("해당하는 멤버가 없습니다."));
    }
    public void createMember(Member member){
        memberRepository.save(member);
    }

    public List<MemberResponse> updateMembers(UUID projectId, List<MemberRequest> updatedMembers) {
        Project project = projectService.getProjectById(projectId);

        return updatedMembers.stream()
                .map(updatedMember -> {
                    User user = userRepository.findUserByEmail(updatedMember.getEmail());
                    if (user != null) {
                        Member member = memberRepository.findByUserAndProject(user, project);
                        if (member != null) {
                            member.setPermissionLevel(updatedMember.getPermissionLevel());
                            memberRepository.save(member);
                            return new MemberResponse(member.getMemberId(), "수정되었습니다.");
                        }
                    }
                    return null;
                })
                .filter(memberResponse -> memberResponse != null)
                .collect(Collectors.toList());
    }

    public List<MemberResponse> deleteMembers(UUID projectId, List<MemberRequest> deletedMembers) {
        Project project = projectService.getProjectById(projectId);

        return deletedMembers.stream()
                .map(deletedMember -> {
                    User user = userService.findUserByEmail(deletedMember.getEmail());
                    if (user != null) {
                        Member member = memberRepository.findByUserAndProject(user, project);
                        if (member != null) {
                            memberRepository.delete(member);
                            return new MemberResponse(member.getMemberId(), "삭제되었습니다.");
                        }
                    }
                    return null;
                })
                .filter(memberResponse -> memberResponse != null)
                .collect(Collectors.toList());
    }

    public List<Project> findMyProject(UUID userId) { return memberRepository.findProjectsByUserId(userId); }
}
