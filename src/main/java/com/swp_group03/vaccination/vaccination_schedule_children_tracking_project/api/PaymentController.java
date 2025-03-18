package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.api;

import com.stripe.model.PaymentIntent;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Payment;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.payment.PaymentRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.ApiResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.order.StripePaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/payment")
@CrossOrigin(origins = "*")
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

    // @PostMapping("/{orderID}/addPayment")
    // public ApiResponse addPayment(@PathVariable int orderID, @RequestBody PaymentRequest request) {
    //     Payment payment = stripePaymentService.processStripePayment(orderID,request);
    @PostMapping("/{orderID}/create-intent")
    public ApiResponse createPaymentIntent(@PathVariable int orderID, @RequestBody PaymentRequest request) {
        try {
            log.info("Received create-intent request for orderID: {}, amount: {}", orderID, request.getAmount());

            PaymentIntent intent = stripePaymentService.createPaymentIntent(orderID, request);

            Map<String, String> response = new HashMap<>();
            response.put("clientSecret", intent.getClientSecret());

            // return ApiResponse.builder()
            //         .code(201)
            //         // .message("Successfully added payment method")
            //         // .result(payment).build();
            //         .message("Payment intent created successfully")
            //         .result(response)
            //         .build();
            log.info("Successfully created payment intent for order: {}", orderID);

            return ApiResponse.builder()
                    .code(201)
                    .message("Payment intent created successfully")
                    .result(response)
                    .build();
        } catch (Exception e) {
            log.error("Error creating payment intent for orderID {}: {}", orderID, e.getMessage());
            return ApiResponse.builder()
                    .code(500)
                    .message("Failed to create payment intent: " + e.getMessage())
                    .build();
        }
    }

    @PostMapping("/{orderID}/confirm")
    public ApiResponse confirmPayment(
            @PathVariable int orderID,
            @RequestBody Map<String, String> confirmRequest) {

        // PaymentRequest paymentRequest = new PaymentRequest();
        // paymentRequest.setAmount(Double.parseDouble(confirmRequest.get("amount")));

        // Payment payment = stripePaymentService.confirmPayment(
        //         orderID,
        //         confirmRequest.get("paymentIntentId"),
        //         paymentRequest
        // );

        // return ApiResponse.builder()
        //         .code(201)
        //         .message("Payment confirmed successfully")
        //         .result(payment)
        //         .build();
        try {
            log.info("Received confirm payment request for orderID: {}, paymentIntentId: {}",
                    orderID, confirmRequest.get("paymentIntentId"));

            if (!confirmRequest.containsKey("paymentIntentId") || confirmRequest.get("paymentIntentId") == null) {
                return ApiResponse.builder()
                        .code(400)
                        .message("Missing payment intent ID")
                        .build();
            }

            if (!confirmRequest.containsKey("amount")) {
                return ApiResponse.builder()
                        .code(400)
                        .message("Missing amount")
                        .build();
            }

            PaymentRequest paymentRequest = new PaymentRequest();
            paymentRequest.setAmount(Double.parseDouble(confirmRequest.get("amount")));

            Payment payment = stripePaymentService.confirmPayment(
                    orderID,
                    confirmRequest.get("paymentIntentId"),
                    paymentRequest
            );

            log.info("Payment confirmed successfully for orderID: {}", orderID);
            return ApiResponse.builder()
                    .code(201)
                    .message("Payment confirmed successfully")
                    .result(payment)
                    .build();
        } catch (Exception e) {
            log.error("Error confirming payment for orderID {}: {}", orderID, e.getMessage());
            return ApiResponse.builder()
                    .code(500)
                    .message("Failed to confirm payment: " + e.getMessage())
                    .build();
        }
    }
}
