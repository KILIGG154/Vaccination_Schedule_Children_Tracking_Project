package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.mapper;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Vaccine;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.VaccineCombo;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.VaccineComboDetail;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.Vaccine.ResponseVaccine;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.Vaccine.ResponseVaccineCombo;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.Vaccine.ResponseVaccineDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface VaccineMapper {

    List<ResponseVaccine> toResponseVaccine(List<Vaccine> vaccines);

    List<ResponseVaccineCombo> toResponseVaccineCombo(List<VaccineCombo> vaccineCombos);

    // Ánh xạ VaccineComboDetail -> ResponseVaccineDetails
    @Mapping(target = "comboName", source = "combo", qualifiedByName = "mapComboName")
    @Mapping(target = "description", source = "combo", qualifiedByName = "mapComboDescription")
    @Mapping(target = "total", source = "combo", qualifiedByName = "mapComboTotal")  // Thêm mapping cho total
    @Mapping(target = "vaccineName", source = "vaccine", qualifiedByName = "mapVaccineName")
    ResponseVaccineDetails toResponseVaccineDetail(VaccineComboDetail vaccineComboDetail);


    // Chuyển đổi danh sách VaccineComboDetail thành danh sách ResponseVaccineDetails
    default List<ResponseVaccineDetails> toResponseVaccineDetails(List<VaccineComboDetail> vaccineComboDetails) {
        if (vaccineComboDetails == null || vaccineComboDetails.isEmpty()) {
            return List.of();
        }
        return vaccineComboDetails.stream()
                .filter(detail -> detail.getCombo() != null && detail.getVaccine() != null) // Kiểm tra null trước khi ánh xạ
                .map(this::toResponseVaccineDetail)
                .collect(Collectors.toList());
    }

    // Đảm bảo @Named nằm trong cùng file
    @Named("mapComboName")
    static String mapComboName(VaccineCombo combo) {
        return Optional.ofNullable(combo)
                .map(VaccineCombo::getComboName)
                .orElse("Unknown Combo");
    }

    @Named("mapComboDescription")
    static String mapComboDescription(VaccineCombo combo) {
        return Optional.ofNullable(combo)
                .map(VaccineCombo::getDescription)
                .orElse("No Description");
    }


    //Map Total Price của VaccineCombo trong Combo qua thằng ResponseVaccineComboDetail
    @Named("mapComboTotal")
    static double mapComboTotal(VaccineCombo combo) {
        return Optional.ofNullable(combo)
                .map(VaccineCombo::getTotal)
                .orElse(0.0);
    }

    @Named("mapVaccineName")
    static String mapVaccineName(Vaccine vaccine) {
        return Optional.ofNullable(vaccine)
                .map(Vaccine::getName)
                .orElse("Unknown Vaccine");
    }

}
