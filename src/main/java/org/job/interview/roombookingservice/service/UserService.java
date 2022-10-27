package org.job.interview.roombookingservice.service;

import org.job.interview.roombookingservice.controller.dto.CreateUserResponse;
import org.job.interview.roombookingservice.controller.dto.UserDTO;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {

    UserDTO findByUsername(@Param("username") String username);

    CreateUserResponse register(UserDTO user, String role);
}
