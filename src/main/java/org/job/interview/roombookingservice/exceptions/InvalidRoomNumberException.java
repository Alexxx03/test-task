package org.job.interview.roombookingservice.exceptions;


import org.springframework.http.HttpStatus;

public class InvalidRoomNumberException extends ResponseStatusWithTimestampEx {

    Long roomNumber;

    public InvalidRoomNumberException(HttpStatus status, String reason, Long roomNumber) {
        super(status, reason);
        this.roomNumber = roomNumber;
    }
}
