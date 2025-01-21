package com.example.service;

import com.example.model.MaterialRequest;
import com.example.model.MaterialRequestItem;
import com.example.repository.MaterialRequestRepository;
import com.example.repository.MaterialRequestItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MaterialRequestService {

    @Autowired
    private MaterialRequestRepository materialRequestRepository;

    @Autowired
    private MaterialRequestItemRepository materialRequestItemRepository;

    // Method untuk membuat material request baru
    @Transactional
    public MaterialRequest createMaterialRequest(MaterialRequest materialRequest, List<MaterialRequestItem> items) {
        // Simpan MaterialRequest terlebih dahulu
        MaterialRequest savedRequest = materialRequestRepository.save(materialRequest);

        // Set MaterialRequestId pada setiap item dan simpan item
        for (MaterialRequestItem item : items) {
            item.setMaterialRequestId(savedRequest.getId()); // Set materialRequestId ke item
            materialRequestItemRepository.save(item); // Simpan item ke dalam database
        }

        return savedRequest;
    }

    public void saveMaterialRequest(MaterialRequest materialRequest) {
        materialRequestRepository.save(materialRequest);
    }

    public List<MaterialRequest> getAllMaterialRequests() {
        return materialRequestRepository.findAll();
    }

    // Method untuk mendapatkan material request berdasarkan id
    public Optional<MaterialRequest> getMaterialRequestById(Integer id) {
        return materialRequestRepository.findById(id);
    }

    // Method untuk mendapatkan material request berdasarkan requesterId
    public List<MaterialRequest> getMaterialRequestsByRequesterId(Integer requesterId) {
        return materialRequestRepository.findByRequesterId(requesterId);
    }

    // Method untuk mendapatkan material request berdasarkan status
    public List<MaterialRequest> getMaterialRequestsByStatus(String status) {
        return materialRequestRepository.findByStatus(status);
    }

    // Method untuk mengupdate status material request
    @Transactional
    public MaterialRequest updateStatus(Integer id, String status) {
        MaterialRequest request = materialRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        request.setStatus(status);
        return materialRequestRepository.save(request);
    }

    // Method untuk mendapatkan semua item dalam material request
    public List<MaterialRequestItem> getItemsByMaterialRequestId(Integer materialRequestId) {
        return materialRequestItemRepository.findByMaterialRequestId(materialRequestId);
    }

}
