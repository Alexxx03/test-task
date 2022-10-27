package org.job.interview.roombookingservice.persistence.repository;

import org.job.interview.roombookingservice.persistence.model.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("select (count(b) > 0) from Booking b " +
            "where b.room.number = :roomNumber and b.date = :date and (b.startTime between :startTime and :endTime - 1 " +
            "or b.endTime between :startTime + 1 and :endTime) ")
    Boolean isRoomAlreadyBooked(@Param("roomNumber") Long roomNumber, @Param("date") Date date,
                                @Param("startTime") Integer startTime, @Param("endTime") Integer endTime);

    @Query(value = "SELECT * FROM booking WHERE YEARWEEK(:date, 1) = YEARWEEK(CURDATE(), 1) " +
                   "AND user_id = :userId ORDER BY date DESC ",
                   countQuery = "SELECT COUNT(*) FROM booking WHERE YEARWEEK(:date, 1) = YEARWEEK(CURDATE(), 1) " +
                   "AND user_id = :userId ORDER BY date DESC ",
                    nativeQuery = true)
    Page<Booking> getAllByUserThisWeek(Pageable pageRequest, @Param("userId") Long userId, @Param("date") Date date);

    Optional<Booking> findByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);
}
