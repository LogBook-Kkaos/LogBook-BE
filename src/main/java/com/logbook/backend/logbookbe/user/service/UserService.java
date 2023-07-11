package com.logbook.backend.logbookbe.user.service;

import com.logbook.backend.logbookbe.user.model.User;
import com.logbook.backend.logbookbe.user.repository.UserRepository;

import com.logbook.backend.logbookbe.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        return userRepository.save(user);
    }
}
