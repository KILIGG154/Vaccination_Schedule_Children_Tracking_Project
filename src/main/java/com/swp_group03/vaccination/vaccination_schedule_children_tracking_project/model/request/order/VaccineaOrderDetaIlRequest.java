package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VaccineaOrderDetaIlRequest {
    private int quantity;
    private int totalPrice;
//    private VaccineOrder vaccineOrder;
//    private vaccine vaccine;
}
