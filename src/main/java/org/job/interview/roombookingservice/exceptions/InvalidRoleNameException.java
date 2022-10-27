package org.job.interview.roombookingservice.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Getter
@Setter
public class InvalidRoleNameException extends ResponseStatusWithTimestampEx {

    public InvalidRoleNameException(HttpStatus status, String reason) {
        super(status, reason);
    }
}
