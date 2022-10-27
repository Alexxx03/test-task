package org.job.interview.roombookingservice.persistence.repository;

import org.job.interview.roombookingservice.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> getByUsername(@Param("username") String username);
}
