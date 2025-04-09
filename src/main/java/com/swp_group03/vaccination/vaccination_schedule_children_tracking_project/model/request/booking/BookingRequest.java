package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.booking;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.BookingStatus;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Child;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {


    private LocalDate appointmentDate;
    private BookingStatus status;

    public BookingRequest(LocalDate appointmentDate, Child child, List<Payment> payments, BookingStatus status) {
        this.appointmentDate = appointmentDate;
        this.status = status;
    }


}
