package com.epam.course.repository;

import com.epam.course.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginUserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}