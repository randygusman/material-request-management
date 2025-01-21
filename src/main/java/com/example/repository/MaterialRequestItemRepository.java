package com.example.repository;

import com.example.model.MaterialRequestItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRequestItemRepository extends JpaRepository<MaterialRequestItem, Integer> {

    // Method untuk mendapatkan item berdasarkan materialRequestId
    List<MaterialRequestItem> findByMaterialRequestId(Integer materialRequestId);
}
