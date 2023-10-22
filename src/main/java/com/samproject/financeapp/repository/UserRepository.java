package com.samproject.financeapp.repository;

import com.samproject.financeapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByGoogleId(String googleId);

}
