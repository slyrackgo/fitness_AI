package com.fitnessAI.userService.repository;

import com.fitnessAI.userService.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {


    boolean existsByEmail(@NotBlank(message = "email is required") @Email(message = "Invalid email format") String email);
}
