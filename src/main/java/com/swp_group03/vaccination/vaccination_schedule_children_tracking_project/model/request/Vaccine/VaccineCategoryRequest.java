package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.Vaccine;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class VaccineCategoryRequest {
    String categoryName;
    String description;
}
