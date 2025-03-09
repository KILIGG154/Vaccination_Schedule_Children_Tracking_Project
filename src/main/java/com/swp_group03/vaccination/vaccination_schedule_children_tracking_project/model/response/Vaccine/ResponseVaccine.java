package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.Vaccine;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.VacineCategory;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ResponseVaccine {

    int id;

    String name;

    String description;

    String manufacturer;

    VacineCategory categoryID;

    String dosage;

    String contraindications;

    String precautions;

    String interactions;

    String adverseReactions;

    String storageConditions;

    String recommended;

    String preVaccination;

    String compatibility;

    String imagineUrl;

    Integer quantity;

//    LocalDate expirationDate;

//    float price;
    double unitPrice;

    double salePrice;

    String status;

}