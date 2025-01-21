package com.example.dto;

public class MaterialRequestItemDTO {
    private String materialName; // Nama material yang diminta
    private Integer quantity; // Jumlah material yang diminta
    private String usageDescription; // Deskripsi penggunaan material

    // Getter and Setter untuk materialName
    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    // Getter and Setter untuk quantity
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    // Getter and Setter untuk usageDescription
    public String getUsageDescription() {
        return usageDescription;
    }

    public void setUsageDescription(String usageDescription) {
        this.usageDescription = usageDescription;
    }
}
