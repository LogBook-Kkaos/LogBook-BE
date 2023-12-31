package com.logbook.backend.logbookbe.domain.user.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.logbook.backend.logbookbe.domain.user.model.User;
import com.logbook.backend.logbookbe.domain.user.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }
        return userRepository.save(user);
    }

    public User getUserById(UUID userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }
  
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> searchUsers(String email) {
        return userRepository.findByEmailContaining(email);
    }

    public void deleteUser(UUID userId) {
        userRepository.deleteById(userId);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("해당하는 사용자 이메일이 없습니다."));
    }
}
