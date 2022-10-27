package org.job.interview.roombookingservice.persistence.repository;

import org.job.interview.roombookingservice.persistence.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room> findByNumber(Long number);
}
