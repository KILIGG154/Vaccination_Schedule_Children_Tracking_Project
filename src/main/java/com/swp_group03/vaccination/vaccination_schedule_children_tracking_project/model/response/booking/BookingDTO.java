package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Booking;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.BookingStatus;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.child.ChildDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {
    private int bookingId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate appointmentDate;
    private ChildDTO child;
    private BookingStatus status;

    public BookingDTO(Booking booking) {
        this.bookingId = booking.getBookingId();
        this.appointmentDate = booking.getAppointmentDate();
        this.child = new ChildDTO(booking.getChild());
        this.status = booking.getStatus();
    }

}
