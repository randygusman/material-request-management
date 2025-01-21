package com.example.repository;

import com.example.model.MaterialRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRequestRepository extends JpaRepository<MaterialRequest, Integer> {

    // Method untuk mendapatkan material request berdasarkan status
    List<MaterialRequest> findByStatus(String status);

    // Method untuk mendapatkan material request berdasarkan requesterId
    List<MaterialRequest> findByRequesterId(Integer requesterId);
}
