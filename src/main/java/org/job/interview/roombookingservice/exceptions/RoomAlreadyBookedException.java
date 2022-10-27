package org.job.interview.roombookingservice.exceptions;

import org.job.interview.roombookingservice.exceptions.ResponseStatusWithTimestampEx;
import org.springframework.http.HttpStatus;

public class RoomAlreadyBookedException extends ResponseStatusWithTimestampEx {

    public RoomAlreadyBookedException(HttpStatus status, String reason) {
        super(status, reason);
    }
}
