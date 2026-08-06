package com.fitnessAI.userService.service;

import com.fitnessAI.userService.dto.RegisterRequest;
import com.fitnessAI.userService.dto.UserResponse;
import com.fitnessAI.userService.model.User;
import com.fitnessAI.userService.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository repository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        repository = mock(UserRepository.class);
        userService = new UserService();
        // inject repository via reflection since field is private and autowired in code
        try {
            java.lang.reflect.Field repoField = UserService.class.getDeclaredField("repository");
            repoField.setAccessible(true);
            repoField.set(userService, repository);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void register_newUser_savesAndReturnsUserResponse() {
        RegisterRequest req = new RegisterRequest();
        req.setEmail("new@example.com");
        req.setPassword("secret");

        when(repository.existsByEmail("new@example.com")).thenReturn(false);

        User saved = new User();
        saved.setId("u-1");
        saved.setEmail("new@example.com");
        saved.setPassword("secret");

        when(repository.save(any(User.class))).thenReturn(saved);

        UserResponse resp = userService.register(req);

        assertNotNull(resp);
        assertEquals("u-1", resp.getId());
        assertEquals("new@example.com", resp.getEmail());
        verify(repository, times(1)).save(any(User.class));
    }

    @Test
    void register_existingUser_returnsExisting() {
        RegisterRequest req = new RegisterRequest();
        req.setEmail("exist@example.com");
        req.setPassword("pw");

        User existing = new User();
        existing.setId("u-2");
        existing.setEmail("exist@example.com");
        existing.setPassword("pw");

        when(repository.existsByEmail("exist@example.com")).thenReturn(true);
        when(repository.findByEmail("exist@example.com")).thenReturn(existing);

        UserResponse resp = userService.register(req);

        assertNotNull(resp);
        assertEquals("u-2", resp.getId());
        assertEquals("exist@example.com", resp.getEmail());
        verify(repository, never()).save(any());
    }
}
