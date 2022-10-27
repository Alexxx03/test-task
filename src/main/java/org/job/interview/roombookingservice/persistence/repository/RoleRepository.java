package org.job.interview.roombookingservice.persistence.repository;

import org.job.interview.roombookingservice.persistence.model.AccessRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<AccessRole, Long> {

    Optional<AccessRole> findByName(String name);
}
