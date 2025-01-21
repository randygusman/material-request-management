package com.example.dto;

import java.time.LocalDateTime;
import java.util.List;

public class MaterialRequestDTO {
    private String projectName; // Nama proyek terkait permintaan
    private Integer requesterId; // ID dari requester (user yang membuat permintaan)
    private String status; // Status permintaan material (e.g., Pending, Approved, Rejected)
    private LocalDateTime createdAt; // Waktu pembuatan permintaan
    private LocalDateTime updatedAt; // Waktu pembaruan terakhir permintaan
    private List<MaterialRequestItemDTO> items; // Daftar item material request

    // Getter and Setter untuk projectName
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    // Getter and Setter untuk requesterId
    public Integer getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(Integer requesterId) {
        this.requesterId = requesterId;
    }

    // Getter and Setter untuk status
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Getter dan Setter untuk createdAt
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // Getter dan Setter untuk updatedAt
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Getter and Setter untuk items
    public List<MaterialRequestItemDTO> getItems() {
        return items;
    }

    public void setItems(List<MaterialRequestItemDTO> items) {
        this.items = items;
    }
}
