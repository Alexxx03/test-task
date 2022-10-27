package org.job.interview.roombookingservice.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import liquibase.pro.packaged.B;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookingDTO {

    private Long id;

    @NotBlank
    private String weekday;

    @Min(8)
    @Max(16)
    @NotNull
    private Integer startTime;

    @Min(9)
    @Max(17)
    @NotNull
    private Integer endTime;

    @NotNull
    @FutureOrPresent
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date date;

    private Long userId;

    @NotNull
    private RoomDTO room;

    public BookingDTO update(BookingDTO other) {
        this.id = other.getId();
        this.startTime = other.getStartTime();
        this.endTime = other.getEndTime();
        this.date = other.getDate();
        this.weekday = other.getWeekday();
        this.userId = other.getUserId();
        this.room = other.getRoom();

        return this;
    }
}
