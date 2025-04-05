package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.api;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Booking;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.ApiResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.booking.BookingRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.booking.ReactionRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.booking.BookingDTO;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.booking.BookingResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.booking.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // 1. Create a new booking
    @PostMapping("/child/{childId}")
    public ApiResponse createBooking(@PathVariable int childId, @RequestBody BookingRequest bookingRequest) {
        BookingDTO booking = bookingService.createBookingRepo(childId, bookingRequest);
        return ApiResponse.builder()
                .code(200)
                .message("Booking created successfully")
                .result(booking)
                .build();
    }

    // 2. Get all bookings
    @GetMapping("/")
    public ApiResponse getAllBookings() {
        List<BookingResponse> bookings = bookingService.getBook();
        return ApiResponse.builder()
        .code(200)
        .message("Bookings retrieved successfully")
        .result(bookings)
        .build();
    }

    // 3. Get a booking by ID
    @GetMapping("/{bookingId}")
    public ApiResponse getBookingById(@PathVariable int bookingId) {
        BookingDTO booking = bookingService.getAllBooking(bookingId);
        return ApiResponse.builder()
        .code(200)
        .message("Booking retrieved successfully")
        .result(booking)
        .build();
    }

    // 4. Update booking date
    @PutMapping("/{bookingId}")
    public ApiResponse updateBookingDate(@PathVariable int bookingId, @RequestBody BookingRequest bookingRequest) {
        Booking booking = bookingService.updateBookingDate(bookingId, bookingRequest);
        return ApiResponse.builder()
        .code(200)
        .message("Booking date updated successfully")
        .result(booking)
        .build();
    }

    // 5. Check-in for an appointment (update status to CHECKED_IN)
    @PutMapping("/{bookingId}/check-in")
    @SuppressWarnings("rawtypes")
    public ApiResponse checkIn(@PathVariable int bookingId) {
        return bookingService.checkIn(bookingId);
    }

    // 6. Update payment status (update status to PAID)
    @PutMapping("/{bookingId}/payment")
    @SuppressWarnings("rawtypes")
    public ApiResponse updatePaymentStatus(@PathVariable int bookingId) {
        return bookingService.waitingForPayment(bookingId);
    }

    // 7. Assign staff to a booking
    @PostMapping("/{bookingId}/assign/{role}")
    @SuppressWarnings("rawtypes")
    public ApiResponse assignStaffToBooking(
            @PathVariable int bookingId,
            @PathVariable String role,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date bookingDate) {
        return bookingService.assignStaffToBooking(bookingId, role, bookingDate);
    }

    // 8. Record post-vaccination reaction
    @PostMapping("/{bookingId}/reaction")
    @SuppressWarnings("rawtypes")
    public ApiResponse recordReaction(@PathVariable int bookingId, @RequestBody ReactionRequest request) {
        return bookingService.recordVaccinationReaction(bookingId, request.getReaction());
    }
}
