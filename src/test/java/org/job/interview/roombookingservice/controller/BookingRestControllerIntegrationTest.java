package org.job.interview.roombookingservice.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.job.interview.roombookingservice.controller.dto.BookingDTO;
import org.job.interview.roombookingservice.controller.dto.RoomDTO;
import org.job.interview.roombookingservice.persistence.model.Booking;
import org.job.interview.roombookingservice.persistence.model.Room;
import org.job.interview.roombookingservice.persistence.model.Weekday;
import org.job.interview.roombookingservice.persistence.repository.BookingRepository;
import org.job.interview.roombookingservice.util.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BookingRestControllerIntegrationTest extends BaseRestControllerIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    WebApplicationContext context;

    private static Long USER_ID = 2L;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithUserDetails(value = "superUser")
    public void getAllBookings_shouldReturnUsersBookings() throws Exception {

        DateWeekdayTuple dateWeekdayTuple = getValidDateWeekdayTuple();


        Booking booking1 = Booking.builder()
                .userId(USER_ID)
                .weekday(dateWeekdayTuple.getWeekday())
                .startTime(12)
                .endTime(13)
                .date(dateWeekdayTuple.getDate())
                .id(null)
                .room(new Room(1L, 1L))
                .build();
        Booking booking2 = Booking.builder()
                .userId(USER_ID)
                .weekday(dateWeekdayTuple.getWeekday())
                .startTime(16)
                .endTime(17)
                .date(dateWeekdayTuple.getDate())
                .id(null)
                .room(new Room(1L, 1L))
                .build();


        bookingRepository.save(booking1);
        bookingRepository.save(booking2);

        mvc.perform(get("/booking/weekly")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*").isNotEmpty())
                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$.content.[0].id").value(booking1.getId()))
                .andExpect(jsonPath("$.content.[0].startTime").value(booking1.getStartTime()))
                .andExpect(jsonPath("$.content.[0].userId").value(booking1.getUserId()))
                .andExpect(jsonPath("$.content.[1].id").value(booking2.getId()))
                .andExpect(jsonPath("$.content.[1].startTime").value(booking2.getStartTime()))
                .andExpect(jsonPath("$.content.[1].userId").value(booking1.getUserId()));
    }

    @Test
    @WithUserDetails("superUser")
    public void createBooking_shouldReturnSavedBooking() throws Exception {

        BookingDTO bookingDTO = BookingDTO.builder()
                .userId(USER_ID)
                .weekday(Weekday.TUESDAY.toString())
                .startTime(9)
                .endTime(10)
                .date(new SimpleDateFormat("dd-MM-yyyy").parse("14-02-2023"))
                .id(null)
                .room(new RoomDTO(1L, 1L))
                .build();

        mvc.perform(post("/booking")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookingDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.room.id").value(bookingDTO.getRoom().getId()))
                .andExpect(jsonPath("$.userId").value(bookingDTO.getUserId()));
    }

    private DateWeekdayTuple getValidDateWeekdayTuple() {
        Map<Integer, Weekday> map = new HashMap<>();
        map.put(1, Weekday.MONDAY);
        map.put(2, Weekday.TUESDAY);
        map.put(3, Weekday.WEDNESDAY);
        map.put(4, Weekday.THURSDAY);
        map.put(5, Weekday.FRIDAY);

        Date date = new Date();
        if (DateUtils.isWeekend(date)) {
            date = org.apache.commons.lang3.time.DateUtils.addDays(date, -2);
        }
        Weekday weekday = map.get(DateUtils.numberOfWeekday(date));
        return new DateWeekdayTuple(weekday, date);
    }


    private static class DateWeekdayTuple {

        private Weekday weekday;
        private Date date;

        public DateWeekdayTuple(Weekday weekday, Date date) {
            this.weekday = weekday;
            this.date = date;
        }

        public Weekday getWeekday() {
            return weekday;
        }

        public Date getDate() {
            return date;
        }
    }
}
