package org.job.interview.roombookingservice.exceptions;


import org.springframework.http.HttpStatus;

public class BookingNotFoundException extends ResponseStatusWithTimestampEx {

    Long userId;
    Long bookingId;

    public BookingNotFoundException(HttpStatus status, String reason, Long bookingId, Long userId) {
        super(status, reason);
        this.bookingId = bookingId;
        this.userId = userId;
    }
}
