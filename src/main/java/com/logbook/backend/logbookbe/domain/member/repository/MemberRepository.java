package com.logbook.backend.logbookbe.domain.member.repository;

import com.logbook.backend.logbookbe.domain.member.model.Member;
import com.logbook.backend.logbookbe.domain.project.model.Project;
import com.logbook.backend.logbookbe.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, UUID> {
    boolean existsByProjectAndUser(Project project, User user);

    List<Member> findByProject(Project project);

    Member findByUserAndProject(User user, Project project);

    Optional<Member> findById(UUID memberId);

    @Query("SELECT DISTINCT m.project FROM Member m WHERE m.user.userId = :userId")
    List<Project> findProjectsByUserId(UUID userId);
    
    Member findByMemberId(UUID memberId);

    @Query("SELECT DISTINCT m.user FROM Member m WHERE m.project.projectId = :projectId")
    List<User> findUsersByProjectId(UUID projectId);
}
