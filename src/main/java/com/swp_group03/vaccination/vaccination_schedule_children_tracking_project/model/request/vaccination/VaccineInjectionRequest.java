package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.vaccination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VaccineInjectionRequest {
        private Long doseId;
        private List<Map<String, Object>> vaccinations;

} 