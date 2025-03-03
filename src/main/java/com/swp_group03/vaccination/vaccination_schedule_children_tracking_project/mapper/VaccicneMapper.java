package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.mapper;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Vaccine;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.VaccineCombo;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.Vaccine.ResponseVaccine;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.Vaccine.ResponseVaccineCombo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VaccicneMapper {

    List<ResponseVaccine> toResponseVaccine(List<Vaccine> vaccines);

    List<ResponseVaccineCombo> toResponseVaccineCombo(List<VaccineCombo> vaccineCombo);
}
