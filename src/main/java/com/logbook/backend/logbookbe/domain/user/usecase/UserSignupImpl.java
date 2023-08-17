package com.logbook.backend.logbookbe.domain.user.usecase;

import com.logbook.backend.logbookbe.domain.user.exception.EmailDuplicateException;
import com.logbook.backend.logbookbe.domain.user.exception.PasswordInvalidException;
import com.logbook.backend.logbookbe.domain.user.model.User;
import com.logbook.backend.logbookbe.domain.user.repository.UserRepository;

import com.logbook.backend.logbookbe.global.jwt.JwtProvider;
import com.logbook.backend.logbookbe.global.jwt.AuthRole;
import com.logbook.backend.logbookbe.global.jwt.dto.JwtResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserSignupImpl implements UserSignup {
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public JwtResponse execute(String userName, String email, String department, String password) throws EmailDuplicateException, PasswordInvalidException {
        // 이메일 중복 체크
        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailDuplicateException();
        }

        // User 객체 생성
        User user = User.builder()
                .userName(userName)
                .email(email)
                .department(department)
                .password(passwordEncoder.encode(password))
                .build();

        // User 객체 저장
        userRepository.save(user);

        String accessToken = jwtProvider.generateToken(user.getEmail(), AuthRole.ROLE_ADMIN, false);
        String refreshToken = jwtProvider.generateToken(user.getEmail(), AuthRole.ROLE_ADMIN, true);
        return new JwtResponse(accessToken, refreshToken);
    }
}
