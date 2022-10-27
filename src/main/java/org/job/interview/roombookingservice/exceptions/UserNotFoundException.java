package org.job.interview.roombookingservice.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ResponseStatusWithTimestampEx {

    private Long userId;

    public UserNotFoundException(HttpStatus status, String reason, Long userId) {
        super(status, reason);
        this.userId = userId;
    }
}
