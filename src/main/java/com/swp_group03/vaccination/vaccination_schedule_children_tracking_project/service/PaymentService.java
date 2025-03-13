package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Booking;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Payment;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.AppException;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.ErrorCode;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.mapper.PaymentMapper;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.Payment.PaymentRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.BookingRepo;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {



    @Autowired
    private PaymentRepo paymentRepo;

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private PaymentMapper paymentMapper;





    public Payment createPayment(int bookingID ,PaymentRequest request) {
        Booking booking = bookingRepo.findById(bookingID).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Payment payment = new Payment();
        payment.setPaymentDate(request.getPaymentDate());
        payment.setStatus(request.isStatus());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setBooking(booking);
        bookingRepo.save(booking);
        return paymentRepo.save(payment);
    }



    public List<Payment> getAllPayment(){
        return paymentRepo.findAll();
    }




}
