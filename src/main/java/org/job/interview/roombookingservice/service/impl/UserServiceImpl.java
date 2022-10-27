package org.job.interview.roombookingservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.job.interview.roombookingservice.controller.dto.CreateUserResponse;
import org.job.interview.roombookingservice.controller.dto.RoleDTO;
import org.job.interview.roombookingservice.controller.dto.UserDTO;
import org.job.interview.roombookingservice.persistence.model.User;
import org.job.interview.roombookingservice.persistence.repository.RoleRepository;
import org.job.interview.roombookingservice.persistence.repository.UserRepository;
import org.job.interview.roombookingservice.security.AuthenticatedUserDetails;
import org.job.interview.roombookingservice.service.UserService;
import org.job.interview.roombookingservice.exceptions.InvalidRoleNameException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;


@Slf4j
@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           BCryptPasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO user = findByUsername(username);

        AuthenticatedUserDetails authenticatedUserDetails = new AuthenticatedUserDetails(user);
        log.info("User with username: {} successfully loaded", username);
        return authenticatedUserDetails;
    }

    @Override
    public UserDTO findByUsername(String username) {
        return modelMapper.map(userRepository.getByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User with username: "  + username + " is not exist")), UserDTO.class);
    }

    @Override
    public CreateUserResponse register(UserDTO user, String role) {
        RoleDTO roleUser = modelMapper.map(roleRepository.findByName(role).orElseThrow(() -> new InvalidRoleNameException(HttpStatus.BAD_REQUEST, "Invalid role name")), RoleDTO.class);
        Set<RoleDTO> userRoles = new HashSet<>(1);
        userRoles.add(roleUser);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);

        User registeredUser = userRepository.save(modelMapper.map(user, User.class));
        log.info("User with username: {} successfully registered", registeredUser.getUsername());
        return new CreateUserResponse(registeredUser);
    }

}
