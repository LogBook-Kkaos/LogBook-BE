package com.logbook.backend.logbookbe.domain.member.service;

import com.logbook.backend.logbookbe.domain.member.model.Member;
import com.logbook.backend.logbookbe.domain.member.repository.MemberRepository;
import com.logbook.backend.logbookbe.domain.project.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public List<Member> getAllUsers() {
        return memberRepository.findAll();
    }

    public void createMember(Member member){
        memberRepository.save(member);
    }

    public List<Project> findMyProject(UUID userId) { return memberRepository.findProjectsByUserId(userId); }
}
