package com.logbook.backend.logbookbe.domain.user.service;

import com.logbook.backend.logbookbe.domain.user.model.User;
import com.logbook.backend.logbookbe.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
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

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
