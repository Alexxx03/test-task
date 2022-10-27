package org.job.interview.roombookingservice.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class UnauthenticatedRequest extends ResponseStatusWithTimestampEx {
    public UnauthenticatedRequest(HttpStatus status, String reason) {
        super(status, reason);
    }
}
