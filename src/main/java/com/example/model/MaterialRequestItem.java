package com.example.model;

import javax.persistence.*;

@Entity
@Table(name = "material_request_items")
public class MaterialRequestItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "material_name", nullable = false)
    private String materialName;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "usage_description", nullable = false)
    private String usageDescription;

    @Column(name = "material_request_id", nullable = false)
    private Integer materialRequestId; // Ganti relasi menjadi ID

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getUsageDescription() {
        return usageDescription;
    }

    public void setUsageDescription(String usageDescription) {
        this.usageDescription = usageDescription;
    }

    public Integer getMaterialRequestId() {
        return materialRequestId;
    }

    public void setMaterialRequestId(Integer materialRequestId) {
        this.materialRequestId = materialRequestId;
    }
}
