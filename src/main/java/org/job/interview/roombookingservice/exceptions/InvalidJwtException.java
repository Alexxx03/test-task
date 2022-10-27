package org.job.interview.roombookingservice.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Getter
@Setter
public class InvalidJwtException extends ResponseStatusWithTimestampEx {
    private String invalidToken;
    public InvalidJwtException(HttpStatus status, String reason, String invalidToken) {
        super(status, reason);
        this.invalidToken = invalidToken;
    }
}
