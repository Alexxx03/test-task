package org.job.interview.roombookingservice.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;
    @NotBlank
    private String username;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private String password;

    @JsonIgnore
    private Set<RoleDTO> roles;

    public List<String> getAuthorities() {
        return roles.stream()
                .flatMap(role ->
                        Stream.concat(
                                Stream.of(role.getName()),
                                role.getPermissions().stream().map(PermissionDTO::getPermissionCode)))
                .distinct()
                .collect(Collectors.toList());
    }
}
