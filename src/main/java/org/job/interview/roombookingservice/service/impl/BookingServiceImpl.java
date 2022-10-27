package org.job.interview.roombookingservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.job.interview.roombookingservice.controller.dto.BookingDTO;
import org.job.interview.roombookingservice.controller.dto.PageableResponse;
import org.job.interview.roombookingservice.controller.dto.RoomDTO;
import org.job.interview.roombookingservice.exceptions.BookingNotFoundException;
import org.job.interview.roombookingservice.exceptions.InvalidRoomNumberException;
import org.job.interview.roombookingservice.exceptions.InvalidWeekdayException;
import org.job.interview.roombookingservice.exceptions.RoomAlreadyBookedException;
import org.job.interview.roombookingservice.exceptions.UserNotFoundException;
import org.job.interview.roombookingservice.persistence.model.Booking;
import org.job.interview.roombookingservice.persistence.repository.BookingRepository;
import org.job.interview.roombookingservice.persistence.repository.RoomRepository;
import org.job.interview.roombookingservice.persistence.repository.UserRepository;
import org.job.interview.roombookingservice.service.BookingService;
import org.job.interview.roombookingservice.util.DateUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;

    public BookingServiceImpl(BookingRepository bookingRepository, UserRepository userRepository,
                              RoomRepository roomRepository, ModelMapper modelMapper) {

        this.bookingRepository = bookingRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public PageableResponse<BookingDTO> getUsersWeeklyBookings(Pageable pageRequest, Long userId) {

        if (userRepository.findById(userId).isEmpty()) {
            log.error("user with id: {} not found", userId);
            throw new UserNotFoundException(HttpStatus.NOT_FOUND, "user is not exist", userId);
        }

        Page<Booking> bookingsPage = bookingRepository.getAllByUserThisWeek(pageRequest, userId, new Date());

        List<BookingDTO> content = bookingsPage.stream()
                .map(booking -> modelMapper.map(booking, BookingDTO.class))
                .collect(Collectors.toList());

        log.info("{} Bookings records were successfully selected for user with id: {} this week", pageRequest.getPageSize(), userId);

        return new PageableResponse<>(content, pageRequest, bookingsPage.getTotalElements());
    }

    @Override
    public BookingDTO getBookingById(Long id, Long userId) {

        BookingDTO bookingDTO = findByIdAndUserId(id, userId);
        log.info("Booking details with id: {} were successfully retrieved for user with id: {}", id, userId);

        return bookingDTO;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public synchronized BookingDTO createBooking(BookingDTO bookingDTO, Long userId) {

        bookingDTO.setUserId(userId);

        if (DateUtils.isWeekend(bookingDTO.getDate())) {
            log.error("invalid date: {}", bookingDTO.getDate());
            throw new InvalidWeekdayException(HttpStatus.BAD_REQUEST, "Room booking is not available on weekend");
        }

        RoomDTO roomDTO = modelMapper.map(roomRepository.findByNumber(bookingDTO.getRoom().getNumber())
                .orElseThrow(() -> {
                    log.error("room with such number: {} does not exists", bookingDTO.getRoom().getNumber());
                    return new InvalidRoomNumberException(HttpStatus.NOT_FOUND, "Invalid room number", bookingDTO.getRoom().getNumber());
                }), RoomDTO.class);

        bookingDTO.setRoom(roomDTO);

        if (bookingRepository.isRoomAlreadyBooked(roomDTO.getNumber(), bookingDTO.getDate(),
                bookingDTO.getStartTime(), bookingDTO.getEndTime())) {
            log.error("Room with number: {} already booked for desired timeframe", roomDTO.getNumber());
            throw new RoomAlreadyBookedException(HttpStatus.CONFLICT, "This room already booked for desired timeframe");
        }

        BookingDTO createdBooking = modelMapper.map(bookingRepository.save(modelMapper.map(bookingDTO, Booking.class)), BookingDTO.class);
        log.info("Booking for room with number: {} successfully created", roomDTO.getNumber());

        return createdBooking;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public synchronized BookingDTO updateBooking(BookingDTO bookingDTO, Long bookingId, Long userId) {

        BookingDTO dbBooking = findByIdAndUserId(bookingId, userId);

        dbBooking.update(bookingDTO);

        return createBooking(dbBooking, userId);
    }

    @Override
    public void deleteBooking(Long id, Long userId) {
        Booking dbBooking = bookingRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> {
                    log.error("Booking with id: {} not found for user with id: {}", id, userId);
                    return new BookingNotFoundException(HttpStatus.NOT_FOUND, "Current user doesn't have chosen booking", id, userId);
                });

        bookingRepository.delete(dbBooking);
        log.info("booking with id: {} was successfully deleted", id);
    }

    private BookingDTO findByIdAndUserId(Long bookingId, Long userId) {

        return modelMapper.map(bookingRepository.findByIdAndUserId(bookingId, userId)
                .orElseThrow(() -> {
                    log.error("Booking with id: {} not found for user with id: {}", bookingId, userId);
                    return new BookingNotFoundException(HttpStatus.NOT_FOUND, "Current user doesn't have chosen booking", bookingId, userId);
                }), BookingDTO.class);
    }
}
