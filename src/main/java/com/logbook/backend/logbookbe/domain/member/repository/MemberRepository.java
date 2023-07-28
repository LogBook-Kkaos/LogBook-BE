package com.logbook.backend.logbookbe.domain.member.repository;

import com.logbook.backend.logbookbe.domain.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {

}
