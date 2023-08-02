package com.logbook.backend.logbookbe.domain.member.repository;

import com.logbook.backend.logbookbe.domain.member.model.Member;
import com.logbook.backend.logbookbe.domain.project.model.Project;
import com.logbook.backend.logbookbe.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByProjectAndUser(Project project, User user);

    List<Member> findByProject(Project project);

    Member findByUserAndProject(User user, Project project);
}
