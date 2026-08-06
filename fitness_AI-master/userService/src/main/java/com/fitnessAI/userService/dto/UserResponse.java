package com.fitnessAI.userService.dto;

import com.fitnessAI.userService.model.userRole;
import lombok.Data;


import java.time.LocalDateTime;
@Data
public class UserResponse {

    private String id;
    private String keycloackId;

    private String email;
    private String firstName;
    private String lastName;

    private userRole role = userRole.USER;




    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
