package org.job.interview.roombookingservice.service;

import org.job.interview.roombookingservice.controller.dto.BookingDTO;
import org.job.interview.roombookingservice.controller.dto.PageableResponse;
import org.springframework.data.domain.Pageable;

public interface BookingService {

    PageableResponse<BookingDTO> getUsersWeeklyBookings(Pageable pageRequest, Long userId);

    BookingDTO getBookingById(Long id, Long userId);

    BookingDTO createBooking(BookingDTO bookingDTO, Long userId);

    BookingDTO updateBooking(BookingDTO bookingDTO, Long bookingId, Long userId);

    void deleteBooking(Long id, Long userId);
}
