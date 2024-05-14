package com.epam.course.repository;

import com.epam.course.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
