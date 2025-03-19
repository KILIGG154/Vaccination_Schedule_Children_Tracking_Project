package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.vaccine;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.vaccine.VaccineProtocolDose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ResponseProtocolDose {

    int vaccineId;
    String vaccineName; // Thêm tên vaccine để dễ hiểu
    List<DoseDTO> doses;

    public void setDoseList(List<VaccineProtocolDose> doseList) {
        if (doseList != null && !doseList.isEmpty()) {
            // Lấy tên vaccine từ dose đầu tiên
            if (doseList.get(0).getVaccine() != null) {
                this.vaccineName = doseList.get(0).getVaccine().getName();
            }

            this.doses = doseList.stream()
                    .map(dose -> {
                        DoseDTO doseDTO = new DoseDTO();
                        doseDTO.setDoseId(dose.getDoseId());

                        // Lấy thông tin từ ProtocolDetail
                        if (dose.getProtocolDetail() != null) {
                            doseDTO.setDoseNumber(dose.getProtocolDetail().getDoseNumber());
                            doseDTO.setIntervalDays(dose.getProtocolDetail().getIntervalDays());

                            // Thêm thông tin về Protocol nếu cần
                            if (dose.getProtocolDetail().getProtocol() != null) {
                                doseDTO.setProtocolId(dose.getProtocolDetail().getProtocol().getProtocolId());
                                doseDTO.setProtocolName(dose.getProtocolDetail().getProtocol().getName());
                            }
                        }

                        return doseDTO;
                    })
                    .collect(Collectors.toList());
        }
    }
}
