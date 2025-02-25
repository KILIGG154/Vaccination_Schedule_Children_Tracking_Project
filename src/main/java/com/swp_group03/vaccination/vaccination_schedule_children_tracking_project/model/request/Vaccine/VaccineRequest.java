package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.Vaccine;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.VacineCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public class VaccineRequest {


    @Size(max = 255)
    @NotNull
    private String name;

    private String description;

    @Size(max = 255)
    private String manufacturer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CategoryID")
    private VacineCategory categoryID;

    @Size(max = 255)
    private String dosage;

    private String contraindications;


    private String precautions;


    private String interactions;


    private String adverseReactions;


    private String storageConditions;


    private String recommended;


    private String preVaccination;


    private String compatibility;

    private String imagineUrl;


    private Integer quantity;


    private LocalDate expirationDate;


    private BigDecimal price;

    @Size(max = 50)
    private String status;

    public VaccineRequest() {
    }

    public VaccineRequest(String name, String description, String manufacturer, VacineCategory categoryID, String dosage, String contraindications, String precautions, String interactions, String adverseReactions, String storageConditions, String recommended, String preVaccination, String compatibility, String imagineUrl, Integer quantity, LocalDate expirationDate, BigDecimal price, String status) {
        this.name = name;
        this.description = description;
        this.manufacturer = manufacturer;
        this.categoryID = categoryID;
        this.dosage = dosage;
        this.contraindications = contraindications;
        this.precautions = precautions;
        this.interactions = interactions;
        this.adverseReactions = adverseReactions;
        this.storageConditions = storageConditions;
        this.recommended = recommended;
        this.preVaccination = preVaccination;
        this.compatibility = compatibility;
        this.imagineUrl = imagineUrl;
        this.quantity = quantity;
        this.expirationDate = expirationDate;
        this.price = price;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public VacineCategory getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(VacineCategory categoryID) {
        this.categoryID = categoryID;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getContraindications() {
        return contraindications;
    }

    public void setContraindications(String contraindications) {
        this.contraindications = contraindications;
    }

    public String getPrecautions() {
        return precautions;
    }

    public void setPrecautions(String precautions) {
        this.precautions = precautions;
    }

    public String getInteractions() {
        return interactions;
    }

    public void setInteractions(String interactions) {
        this.interactions = interactions;
    }

    public String getAdverseReactions() {
        return adverseReactions;
    }

    public void setAdverseReactions(String adverseReactions) {
        this.adverseReactions = adverseReactions;
    }

    public String getStorageConditions() {
        return storageConditions;
    }

    public void setStorageConditions(String storageConditions) {
        this.storageConditions = storageConditions;
    }

    public String getRecommended() {
        return recommended;
    }

    public void setRecommended(String recommended) {
        this.recommended = recommended;
    }

    public String getPreVaccination() {
        return preVaccination;
    }

    public void setPreVaccination(String preVaccination) {
        this.preVaccination = preVaccination;
    }

    public String getCompatibility() {
        return compatibility;
    }

    public void setCompatibility(String compatibility) {
        this.compatibility = compatibility;
    }

    public String getImagineUrl() {
        return imagineUrl;
    }

    public void setImagineUrl(String imagineUrl) {
        this.imagineUrl = imagineUrl;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
