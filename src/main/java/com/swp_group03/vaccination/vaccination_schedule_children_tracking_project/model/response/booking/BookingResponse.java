package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Booking;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.BookingStatus;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Child;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.child.ChildDTO;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.order.VaccineOrderDTO;
import lombok.*;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {
    private int bookingId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date appointmentDate;
    private ChildDTO child;
    private List<VaccineOrderDTO> order;
    private BookingStatus status;

    public BookingResponse(int bookingId, Date appointmentDate, Child child, List<VaccineOrderDTO> order, BookingStatus status) {
        this.bookingId = bookingId;
        this.appointmentDate = appointmentDate;
        this.child = new ChildDTO(child);
        this.order = order;
        this.status = status;
    }

}
