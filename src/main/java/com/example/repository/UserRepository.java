package com.example.repository;

import com.example.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findById(int id); // Sudah disediakan oleh JpaRepository, jadi tidak perlu implementasi tambahan

}
