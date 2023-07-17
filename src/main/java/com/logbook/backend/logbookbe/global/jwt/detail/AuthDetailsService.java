package com.logbook.backend.logbookbe.global.jwt.detail;

import com.logbook.backend.logbookbe.domain.user.model.User;
import com.logbook.backend.logbookbe.domain.user.repository.UserRepository;
import com.logbook.backend.logbookbe.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(ErrorCode.USER_NOT_FOUND.getMessage()));
        return new AuthDetails(user.getEmail());
    }
}
