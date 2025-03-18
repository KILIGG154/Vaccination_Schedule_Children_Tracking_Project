package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Booking;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.child.ChildDTO;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.order.VaccineOrderDTO;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingDTO {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date appointmentDate;
    private ChildDTO child;
    private List<VaccineOrderDTO> order;
    private boolean status;

    public BookingDTO(Booking booking) {
        this.appointmentDate = booking.getAppointmentDate();
        this.child = new ChildDTO(booking.getChild());
        this.status = booking.isStatus();
        this.order = booking.getVaccineOrders().stream().map(VaccineOrderDTO::new).collect(Collectors.toList());
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public ChildDTO getChild() {
        return child;
    }

    public void setChild(ChildDTO child) {
        this.child = child;
    }

    public List<VaccineOrderDTO> getOrder() {
        return order;
    }

    public void setOrder(List<VaccineOrderDTO> order) {
        this.order = order;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
