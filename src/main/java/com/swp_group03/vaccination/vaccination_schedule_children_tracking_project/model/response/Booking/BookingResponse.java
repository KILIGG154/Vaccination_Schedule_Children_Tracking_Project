package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.Booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Child;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.child.ChildDTO;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.order.VaccineOrderDTO;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingResponse {
    private int bookingId;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date appointmentDate;
    
    private ChildDTO child;
    private List<VaccineOrderDTO> order;
    private boolean status;

    public BookingResponse() {
    }
    
    public BookingResponse(int bookingId, Date appointmentDate, Child child, List<VaccineOrderDTO> order, boolean status) {
        this.bookingId = bookingId;
        this.appointmentDate = appointmentDate;
        this.child = new ChildDTO(child);
        this.order = order;
        this.status = status;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
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
