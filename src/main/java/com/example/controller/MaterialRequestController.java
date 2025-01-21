package com.example.controller;

import com.example.dto.*;
import com.example.model.MaterialRequest;
import com.example.model.MaterialRequestItem;
import com.example.service.MaterialRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/material-requests")
public class MaterialRequestController {

    @Autowired
    private MaterialRequestService materialRequestService;

    @PostMapping
    public ResponseEntity<?> createMaterialRequest(@RequestBody MaterialRequestDTO materialRequestDTO) {
        // Validasi data utama
        if (materialRequestDTO == null || materialRequestDTO.getRequesterId() == null ||
                materialRequestDTO.getItems() == null || materialRequestDTO.getItems().isEmpty() ||
                materialRequestDTO.getProjectName() == null || materialRequestDTO.getProjectName().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("Invalid data: Requester ID, Project Name, and items are required.");
        }

        // Validasi daftar item
        List<MaterialRequestItem> items = new ArrayList<>();
        for (MaterialRequestItemDTO itemDTO : materialRequestDTO.getItems()) {
            if (itemDTO.getMaterialName() == null || itemDTO.getMaterialName().isEmpty() ||
                    itemDTO.getQuantity() == null || itemDTO.getQuantity() <= 0 ||
                    itemDTO.getUsageDescription() == null || itemDTO.getUsageDescription().isEmpty()) {
                return ResponseEntity.badRequest().body("Invalid item data: All fields are required for each item.");
            }

            // Membuat item baru berdasarkan data dari DTO
            MaterialRequestItem item = new MaterialRequestItem();
            item.setMaterialName(itemDTO.getMaterialName());
            item.setQuantity(itemDTO.getQuantity());
            item.setUsageDescription(itemDTO.getUsageDescription());
            items.add(item);
        }

        // Buat entitas MaterialRequest
        MaterialRequest materialRequest = new MaterialRequest();
        materialRequest.setRequesterId(materialRequestDTO.getRequesterId());
        materialRequest.setProjectName(materialRequestDTO.getProjectName().trim()); // Simpan projectName
        materialRequest.setStatus("Pending Approval");

        // Panggil service untuk menyimpan material request
        MaterialRequest savedMaterialRequest = materialRequestService.createMaterialRequest(materialRequest, items);

        // Kembalikan respons sukses
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMaterialRequest);
    }

    // Endpoint untuk mendapatkan semua material requests
    @GetMapping
    public ResponseEntity<List<MaterialRequest>> getAllMaterialRequests() {
        List<MaterialRequest> requests = materialRequestService.getAllMaterialRequests();
        return ResponseEntity.ok(requests);
    }

    // Endpoint untuk mendapatkan material requests berdasarkan requesterId
    @GetMapping("/requester/{requesterId}")
    public List<MaterialRequest> getMaterialRequestsByRequester(@PathVariable Integer requesterId) {
        return materialRequestService.getMaterialRequestsByRequesterId(requesterId);
    }

    // Endpoint untuk mendapatkan material request berdasarkan status
    @GetMapping("/status/{status}")
    public List<MaterialRequest> getMaterialRequestsByStatus(@PathVariable String status) {
        return materialRequestService.getMaterialRequestsByStatus(status);
    }

    // Endpoint untuk mengupdate status material request
    @PutMapping("/{id}/status/{status}")
    public MaterialRequest updateMaterialRequestStatus(@PathVariable Integer id, @PathVariable String status) {
        return materialRequestService.updateStatus(id, status);
    }

    // Endpoint untuk mendapatkan material request item berdasarkan
    // materialRequestId
    @GetMapping("/{materialRequestId}/items")
    public List<MaterialRequestItem> getItemsByMaterialRequestId(@PathVariable Integer materialRequestId) {
        return materialRequestService.getItemsByMaterialRequestId(materialRequestId);
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<?> rejectMaterialRequest(@PathVariable Integer id, @RequestBody String rejectionReason) {
        try {
            // Ambil material request berdasarkan ID
            Optional<MaterialRequest> optionalMaterialRequest = materialRequestService.getMaterialRequestById(id);
            if (!optionalMaterialRequest.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Material request not found.");
            }

            MaterialRequest materialRequest = optionalMaterialRequest.get();
            materialRequest.setStatus("Rejected");

            // Pastikan alasan penolakan adalah string dan valid
            if (rejectionReason == null || rejectionReason.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Rejection reason is required.");
            }

            // Set alasan penolakan
            materialRequest.setRejectedReason(rejectionReason.trim());

            // Simpan perubahan
            materialRequestService.saveMaterialRequest(materialRequest);

            return ResponseEntity.ok("Material request has been rejected.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to reject material request: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<?> approveMaterialRequest(@PathVariable Integer id) {
        try {
            Optional<MaterialRequest> optionalMaterialRequest = materialRequestService.getMaterialRequestById(id);

            if (!optionalMaterialRequest.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Material request not found.");
            }

            MaterialRequest materialRequest = optionalMaterialRequest.get();
            materialRequest.setStatus("Approved");

            materialRequestService.saveMaterialRequest(materialRequest);

            return ResponseEntity.ok("Material request has been approved.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to approve material request: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<?> deleteMaterialRequest(@PathVariable Integer id) {
        try {
            Optional<MaterialRequest> optionalMaterialRequest = materialRequestService.getMaterialRequestById(id);

            if (!optionalMaterialRequest.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Material request not found.");
            }

            MaterialRequest materialRequest = optionalMaterialRequest.get();
            materialRequest.setStatus("Deleted");

            materialRequestService.saveMaterialRequest(materialRequest);

            return ResponseEntity.ok("Material request has been deleted.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to approve material request: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/view")
    public ResponseEntity<?> viewMaterialRequest(@PathVariable Integer id) {
        // Ambil material request berdasarkan ID
        Optional<MaterialRequest> materialRequest = materialRequestService.getMaterialRequestById(id);

        if (materialRequest == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Material Request not found.");
        }

        // Ambil items terkait berdasarkan request_id
        List<MaterialRequestItem> items = materialRequestService.getItemsByMaterialRequestId(id);

        // Bungkus response dengan material request dan items
        Map<String, Object> response = new HashMap<>();
        response.put("materialRequest", materialRequest);
        response.put("items", items);

        return ResponseEntity.ok(response);
    }

}
