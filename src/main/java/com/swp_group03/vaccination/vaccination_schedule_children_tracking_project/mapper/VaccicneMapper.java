package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.mapper;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Vaccine;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.VaccineCombo;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.VaccineComboDetail;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.Vaccine.ResponseVaccine;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.Vaccine.ResponseVaccineCombo;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.Vaccine.ResponseVaccineDetails;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface VaccicneMapper {

    List<ResponseVaccine> toResponseVaccine(List<Vaccine> vaccines);

    List<ResponseVaccine> togetVaccine(List<Vaccine> getVaccines);

    List<ResponseVaccineCombo> toResponseVaccineCombo(List<VaccineCombo> vaccineCombo);
    List<ResponseVaccineDetails> toResponseVaccineComboDetails(List<VaccineComboDetail> vaccineComboDetail);
}
