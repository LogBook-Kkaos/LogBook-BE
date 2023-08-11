package com.logbook.backend.logbookbe.domain.user.service;

import com.logbook.backend.logbookbe.domain.user.model.User;
import com.logbook.backend.logbookbe.domain.user.repository.UserRepository;
import com.logbook.backend.logbookbe.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User newUser;
    private UUID userId;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        newUser = new User();
        newUser.setEmail("test@example.com");

        userId = UUID.randomUUID();
    }

    @Test
    @DisplayName("사용자 생성 테스트")
    public void testCreateUser() {
        when(userRepository.findByEmail(newUser.getEmail())).thenReturn(Optional.of(newUser));

        assertThrows(RuntimeException.class, () -> userService.createUser(newUser));
        verify(userRepository, times(0)).save(newUser);
    }

    @Test
    @DisplayName("사용자 조회 테스트")
    public void testGetUserById() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(user));

        User retrievedUser = userService.getUserById(userId);

        assertNotNull(retrievedUser);
        assertEquals(user, retrievedUser);
    }

    @Test
    @DisplayName("사용자 업데이트 테스트")
    public void testUpdateUser() {
        User user = new User();
        when(userRepository.save(user)).thenReturn(user);

        User updatedUser = userService.updateUser(user);

        assertNotNull(updatedUser);
        assertEquals(user, updatedUser);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    @DisplayName("모든 사용자 조회 테스트")
    public void testGetAllUsers() {
        List<User> users = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(users);

        List<User> retrievedUsers = userService.getAllUsers();

        assertNotNull(retrievedUsers);
        assertEquals(users, retrievedUsers);
    }

    @Test
    @DisplayName("사용자 검색 테스트")
    public void testSearchUsers() {
        String email = "test@example.com";
        List<User> users = new ArrayList<>();
        when(userRepository.findByEmailContaining(email)).thenReturn(users);

        List<User> retrievedUsers = userService.searchUsers(email);

        assertNotNull(retrievedUsers);
        assertEquals(users, retrievedUsers);
    }

    @Test
    @DisplayName("사용자 삭제 테스트")
    public void testDeleteUser() {
        UUID userId = UUID.randomUUID();
        doNothing().when(userRepository).deleteById(userId);

        userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    @DisplayName("이메일로 사용자 조회 테스트")
    public void testFindUserByEmail() {
        String email = "test@example.com";
        User user = new User();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        User retrievedUser = userService.findUserByEmail(email);

        assertNotNull(retrievedUser);
        assertEquals(user, retrievedUser);
    }
}
