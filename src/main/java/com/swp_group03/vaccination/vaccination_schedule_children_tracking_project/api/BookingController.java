package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.api;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.ApiResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.booking.BookingRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.booking.ReactionRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.booking.BookingDTO;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.booking.BookingResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.booking.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;


    // 1. Tạo booking
    @PostMapping("/{childID}/create")
    public ApiResponse<BookingDTO> createBooking(@PathVariable int childID, @RequestBody BookingRequest bookingDTO){
        BookingDTO booing = bookingService.createBookingRepo(childID,bookingDTO);
        return ApiResponse.<BookingDTO>builder()
                .code(201)
                .message("Successfully created booking method")
                .result(booing)
                .build();
    }

    @GetMapping("/all")
    public ApiResponse<List<BookingResponse>> getAll() {
        try {
            List<BookingResponse> bookings = bookingService.getBook();
            
            return ApiResponse.<List<BookingResponse>>builder()
                    .code(200)
                    .message(bookings.isEmpty() ? "No bookings found" : "Successfully get all booking")
                    .result(bookings)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<List<BookingResponse>>builder()
                    .code(500)
                    .message("Error retrieving bookings: " + e.getMessage())
                    .build();
        }
    }

    // 2. Cập nhật trạng thái booking (thanh toán)
    @PutMapping("/{bookingId}/payment")
    public ApiResponse updateBookingStatusPaymentStatus(
            @PathVariable int bookingId) {
        return bookingService.waitingForPayment(bookingId);
    }

    // 3. Check-in tại cơ sở
    @PutMapping("/{bookingId}/checkin")
    public ApiResponse checkIn(
            @PathVariable int bookingId) {
        return bookingService.checkIn(bookingId);
    }
    
    // 4. Assign staff to booking
    @PutMapping("/{bookingId}/assign")
    public ApiResponse assignStaff(
            @PathVariable int bookingId) {
        return bookingService.assignStaff(bookingId);
    }
    

    
    // 7. Record post-vaccination reaction
    @PostMapping("/{bookingId}/reaction")
    public ApiResponse recordVaccinationReaction(
            @PathVariable int bookingId,
            @RequestBody ReactionRequest request) {
        return bookingService.recordVaccinationReaction(bookingId, request.getReaction());
    }

}
