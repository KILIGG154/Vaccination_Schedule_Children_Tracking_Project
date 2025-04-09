package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Booking;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.BookingStatus;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Child;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.child.ChildDTO;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.order.VaccineOrderDTO;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {
    private int bookingId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate appointmentDate;
    private ChildDTO child;
    private List<VaccineOrderDTO> order;
    private BookingStatus status;

    // // Constructor nhận Child entity và chuyển đổi thành ChildDTO
    // public BookingResponse(int bookingId, Date appointmentDate, Child child, List<VaccineOrderDTO> order, BookingStatus status) {
    //     this.bookingId = bookingId;
    //     this.appointmentDate = appointmentDate;
    //     this.child = child != null ? new ChildDTO(child) : null;
    //     this.order = order;
    //     this.status = status;
    // }
    
    // Thêm constructor nhận trực tiếp entity Booking
    public BookingResponse(Booking booking) {
        if (booking != null) {
            this.bookingId = booking.getBookingId();
            this.appointmentDate = booking.getAppointmentDate();
            
            // Chuyển đổi Child thành ChildDTO
            if (booking.getChild() != null) {
                this.child = new ChildDTO(booking.getChild());
            }
            
            // Chuyển đổi VaccineOrders thành VaccineOrderDTO
            if (booking.getVaccineOrders() != null) {
                this.order = booking.getVaccineOrders().stream()
                    .filter(order -> order != null)
                    .map(VaccineOrderDTO::new)
                    .collect(Collectors.toList());
            }
            
            this.status = booking.getStatus();
        }
    }
}
