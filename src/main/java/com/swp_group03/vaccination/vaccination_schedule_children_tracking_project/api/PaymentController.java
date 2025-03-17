package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.api;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Payment;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.Payment.PaymentRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.ApiResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.Order.StripePaymentService;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

//    @Autowired
//    private PaymentService paymentService;
//
//
//    @PostMapping("/{orderId}/addPayment")
//    public ApiResponse addPayment(@PathVariable int orderId ,@RequestBody PaymentRequest request) {
//        Payment payments = paymentService.createPayment(orderId,request);
//        return ApiResponse.builder().code(201).message("Successfully added payment method").build();
//    }

    @Autowired
    private StripePaymentService stripePaymentService;

    @PostMapping("/{orderID}/addPayment")
    public ApiResponse addPayment(@PathVariable int orderID, @RequestBody PaymentRequest request) {
        Payment payment = stripePaymentService.processStripePayment(orderID,request);
        return ApiResponse.builder()
                .code(201)
                .message("Successfully added payment method")
                .result(payment).build();
    }

}
