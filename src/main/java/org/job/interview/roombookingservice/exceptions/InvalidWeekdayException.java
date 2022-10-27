package org.job.interview.roombookingservice.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidWeekdayException extends ResponseStatusWithTimestampEx {


    public InvalidWeekdayException(HttpStatus status, String reason) {
        super(status, reason);
    }
}
