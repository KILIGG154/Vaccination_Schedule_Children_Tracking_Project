package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.Vaccine;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.VaccineCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ResponseVaccine {

    int id;

    String name;

    Long categoryID;

    String categoryName;

    String description;

    String manufacturer;

//    VaccineCategory categoryID;

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