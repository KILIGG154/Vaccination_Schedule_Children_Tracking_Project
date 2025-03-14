package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.api;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Booking;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.booking.BookingRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.ApiResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.booking.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/{childID}/create")
    public ApiResponse createBooking(@PathVariable int childID, @RequestBody BookingRequest bookingDTO){
        Booking booing = bookingService.createBookingRepo(childID,bookingDTO);
        return ApiResponse.builder().code(201).message("Successfully created booking method").build();
    }

}
