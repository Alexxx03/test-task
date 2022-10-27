package org.job.interview.roombookingservice.controller;

import org.job.interview.roombookingservice.controller.dto.BookingDTO;
import org.job.interview.roombookingservice.controller.dto.PageableResponse;
import org.job.interview.roombookingservice.service.BookingService;
import org.job.interview.roombookingservice.util.UserUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "/booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/weekly")
    @PreAuthorize("hasAuthority('PERMISSION_READ')")
    @ResponseStatus(HttpStatus.OK)
    public PageableResponse<BookingDTO> getAllByThisWeek(Pageable pageRequest) {
        Long userId = UserUtil.getLoggedInUser().getId();

        return bookingService.getUsersWeeklyBookings(pageRequest, userId);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PERMISSION_READ')")
    @ResponseStatus(HttpStatus.OK)
    public BookingDTO getBookingById(@PathVariable Long id) {
        Long userId = UserUtil.getLoggedInUser().getId();

        return bookingService.getBookingById(id, userId);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('PERMISSION_CREATE')")
    @ResponseStatus(HttpStatus.CREATED)
    public BookingDTO createBooking(@RequestBody @Valid BookingDTO bookingDTO) {
        Long userId = UserUtil.getLoggedInUser().getId();

        return bookingService.createBooking(bookingDTO, userId);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('PERMISSION_UPDATE')")
    @ResponseStatus(HttpStatus.OK)
    public BookingDTO updateBooking(@RequestBody @Valid BookingDTO bookingDTO, @PathVariable("id") Long id) {
        Long userId = UserUtil.getLoggedInUser().getId();

        return bookingService.updateBooking(bookingDTO, id, userId);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('PERMISSION_DELETE')")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBooking(@PathVariable("id") Long id) {
        Long userId = UserUtil.getLoggedInUser().getId();

        bookingService.deleteBooking(id, userId);
    }

}
