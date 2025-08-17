package com.fitnessAI.userService.service;

import com.fitnessAI.userService.dto.RegisterRequest;
import com.fitnessAI.userService.dto.UserResponse;
import com.fitnessAI.userService.model.User;
import com.fitnessAI.userService.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository repository;

    public UserResponse register(@Valid RegisterRequest request) {
        if (repository.existsByEmail(request.getEmail())) {
            User existingUser = repository.findByEmail(request.getEmail());
            UserResponse userResponse = new UserResponse();
            userResponse.setId(existingUser.getId());
            userResponse.setEmail(existingUser .getEmail());
            userResponse.setKeycloackId(existingUser.getKeycloackId());
            userResponse.setPassword(existingUser.getPassword());
            userResponse.setFirstName(existingUser.getFirstName());
            userResponse.setLastName(existingUser.getLastName());
            userResponse.setCreatedAt(existingUser.getCreatedAt());
            userResponse.setUpdatedAt(existingUser.getUpdatedAt());
            return userResponse;
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setKeycloackId(request.getKeycloackId());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        User savedUser = repository.save(user);
        UserResponse userResponse = new UserResponse();
        userResponse.setId(savedUser.getId());
        userResponse.setEmail(savedUser.getEmail());
        userResponse.setKeycloackId(savedUser.getKeycloackId());
        userResponse.setPassword(savedUser.getPassword());
        userResponse.setFirstName(savedUser.getFirstName());
        userResponse.setLastName(savedUser.getLastName());
        userResponse.setCreatedAt(savedUser.getCreatedAt());
        userResponse.setUpdatedAt(savedUser.getUpdatedAt());
        return userResponse;
    }
        public UserResponse getUserProfile(String userId){
            User user = repository.findById(userId).
                    orElseThrow(()-> new RuntimeException("User Not Found"));
            UserResponse userResponse = new UserResponse();
            userResponse.setId(user.getId());
            userResponse.setEmail(user.getEmail());
            userResponse.setPassword(user.getPassword());
            userResponse.setFirstName(user.getFirstName());
            userResponse.setLastName(user.getLastName());
            userResponse.setCreatedAt(user.getCreatedAt());
            userResponse.setUpdatedAt(user.getUpdatedAt());
            return userResponse;
        }

    public Boolean existByUserId(String userId) {
        log.info("Calling User Validation API for userId: {}"  + userId);
        return repository.existsByKeycloackId(userId);
    }
}


