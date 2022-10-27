package org.job.interview.roombookingservice.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Getter
@Setter
public class InvalidAuthenticationType extends ResponseStatusWithTimestampEx {
    public InvalidAuthenticationType(HttpStatus status, String reason) {
        super(status, reason);
    }
}
