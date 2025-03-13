package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Booking;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Child;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.AppException;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.ErrorCode;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.booking.BookingRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.Booking.BookingDTO;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.BookingRepo;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.ChildRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private ChildRepo childRepo;

    public Booking createBookingRepo(int childID, BookingRequest bookingRequest) {
        Child child = childRepo.findById(childID).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Booking booking = new Booking();
        booking.setAppointmentDate(bookingRequest.getAppointmentDate());
        booking.setStatus(booking.isStatus());
        booking.setChild(child);
        childRepo.save(child);
        return bookingRepo.save(booking);
    }

    public BookingDTO getAllBooking(int bookingID){
        Booking booking = bookingRepo.findById(bookingID).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return new BookingDTO(booking);
    }
}
