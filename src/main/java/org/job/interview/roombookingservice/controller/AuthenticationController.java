package org.job.interview.roombookingservice.controller;

import org.job.interview.roombookingservice.controller.dto.CreateUserResponse;
import org.job.interview.roombookingservice.controller.dto.LoginRequestDTO;
import org.job.interview.roombookingservice.controller.dto.LoginResponseDTO;
import org.job.interview.roombookingservice.controller.dto.UserDTO;
import org.job.interview.roombookingservice.security.jwt.JwtTokenProvider;
import org.job.interview.roombookingservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {


    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    public AuthenticationController( AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        try {
            String username = loginRequest.getUsername();
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, loginRequest.getPassword()));
            String token = jwtTokenProvider.createToken(username, authenticate.getAuthorities());

            return ResponseEntity.ok(new LoginResponseDTO(username, token));
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<CreateUserResponse> signup(@Valid @RequestBody UserDTO userDTO) {
        CreateUserResponse newUser = userService.register(userDTO, "ROLE_USER");
        return ResponseEntity.ok(newUser);
    }
}
