package org.job.interview.roombookingservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.job.interview.roombookingservice.persistence.model.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserResponse {
    private String username;
    private String firstName;
    private String lastName;

    public CreateUserResponse(User user) {
        CreateUserResponse userResponse = new CreateUserResponse();
        userResponse.setUsername(user.getUsername());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
    }
}
