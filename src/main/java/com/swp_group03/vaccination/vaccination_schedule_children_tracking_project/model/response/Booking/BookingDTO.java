package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.Booking;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Booking;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Child;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Payment;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.Payment.PaymentDTO;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.order.VaccineOrderDTO;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class BookingDTO {
    private Date appointmentDate;
    private Child child;
    private List<VaccineOrderDTO> order;
    private boolean status;

    public BookingDTO(Booking booking) {
        this.appointmentDate = booking.getAppointmentDate();
        this.child = booking.getChild();
        this.status = booking.isStatus();
        this.order = booking.getVaccineOrders().stream().map(VaccineOrderDTO::new).collect(Collectors.toList());
    }
}
