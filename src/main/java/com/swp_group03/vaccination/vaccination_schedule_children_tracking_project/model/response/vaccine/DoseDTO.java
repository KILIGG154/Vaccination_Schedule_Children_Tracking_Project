package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.vaccine;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
class DoseDTO {
    private long doseId;
    private int doseNumber;
    private int intervalDays;
    private Long protocolId;
    private String protocolName;

    // Constructor đơn giản cho trường hợp chỉ cần thông tin cơ bản
    public DoseDTO(long doseId, int doseNumber, int intervalDays) {
        this.doseId = doseId;
        this.doseNumber = doseNumber;
        this.intervalDays = intervalDays;
    }
}
