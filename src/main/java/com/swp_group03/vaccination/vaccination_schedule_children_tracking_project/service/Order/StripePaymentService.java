package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.Order;

import com.stripe.exception.StripeException;
// import com.stripe.model.Charge;
import com.stripe.model.PaymentIntent;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.PaymentStatus;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Payment;
// import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.VaccineOrder;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.AppException;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.ErrorCode;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.payment.PaymentRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.payment.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StripePaymentService {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private StripeService stripeService;

    // @Autowired
    // private VaccineOrderRepo vaccineOrderRepo;

    // public Payment processStripePayment(int orderID, PaymentRequest request) {
    //     log.info("Get token: " + request.getToken());
    public PaymentIntent createPaymentIntent(int orderID, PaymentRequest request) {
        log.info("Creating payment intent for orderID: {}, amount: {}", orderID, request.getAmount());
        try {
            // // Xử lý thanh toán qua Stripe
            // Charge charge = stripeService.chargeCard(
            //         request.getToken(),
            //         request.getAmount(),
            //         "vnd" // hoặc "USD" tùy vào loại tiền tệ bạn sử dụng
            // );
            // log.info("Charge status: " + charge.getPaid());
            // // Nếu thanh toán thành công
            // if (charge.getPaid()) {
            //     // Cập nhật request với thông tin từ Stripe
            // Create PaymentIntent
            PaymentIntent intent = stripeService.createPaymentIntent(request.getAmount());
            log.info("Created PaymentIntent: {}", intent.getId());
            return intent;
        } catch (StripeException e) {
            log.error("Error creating payment intent: {}", e.getMessage());
            log.error("Stripe Exception details: ", e);
            throw new AppException(ErrorCode.PAYMENT_FAIL);
        } catch (Exception e) {
            log.error("Unexpected error in createPaymentIntent: {}", e.getMessage());
            log.error("Exception details: ", e);
            throw new AppException(ErrorCode.PAYMENT_FAIL);
        }
    }

    public Payment confirmPayment(int orderID, String paymentIntentId, PaymentRequest request) {
        if (paymentIntentId == null || paymentIntentId.isEmpty()) {
            log.error("Empty payment intent ID for orderID: {}", orderID);
            throw new AppException(ErrorCode.PAYMENT_FAIL);
        }

        try {
            log.info("Confirming payment intent: {} for orderID: {}", paymentIntentId, orderID);
            // Confirm the payment intent
            PaymentIntent confirmedIntent = stripeService.confirmPaymentIntent(paymentIntentId);
            log.info("Payment intent status: {}", confirmedIntent.getStatus());

            if ("succeeded".equals(confirmedIntent.getStatus())) {
                // Update request with success status
                request.setStatus(PaymentStatus.COMPLETED);
                // request.setPaymentMethod("MasterCard");

                // Sử dụng service hiện có để lưu thông tin thanh toán
                request.setPaymentMethod("Credit Card");

                // Save payment information
                Payment payment = paymentService.createPayment(orderID, request);
                log.info("Payment saved successfully for orderID: {}", orderID);
                return payment;
            } else {
                // Handle failed payment
                log.warn("Payment failed with status: {} for orderID: {}", confirmedIntent.getStatus(), orderID);
                request.setStatus(PaymentStatus.FAIL);
                // request.setPaymentMethod("MasterCard");
                // return paymentService.createPayment(orderID, request);

                request.setPaymentMethod("Credit Card");
                Payment failedPayment = paymentService.createPayment(orderID, request);
                throw new AppException(ErrorCode.PAYMENT_FAIL);
            }
        } catch (StripeException e) {
            // Xử lý ngoại lệ từ Stripe
            log.error("Stripe error confirming payment for orderID {}: {}, code: {}",
                    orderID, e.getMessage(), e.getCode());
            log.error("Stripe exception details:", e);

            // Create a failed payment record in the database
            request.setStatus(PaymentStatus.FAIL);
            request.setPaymentMethod("Credit Card");
            try {
                paymentService.createPayment(orderID, request);
            } catch (Exception ex) {
                log.error("Failed to record payment failure: {}", ex.getMessage());
            }

            throw new AppException(ErrorCode.PAYMENT_FAIL);
        } catch (Exception e) {
            log.error("Unexpected error in confirmPayment for orderID {}: {}", orderID, e.getMessage());
            log.error("Exception details:", e);

            // Create a failed payment record
            request.setStatus(PaymentStatus.FAIL);
            request.setPaymentMethod("Credit Card");
            try {
                paymentService.createPayment(orderID, request);
            } catch (Exception ex) {
                log.error("Failed to record payment failure: {}", ex.getMessage());
            }

            throw new AppException(ErrorCode.PAYMENT_FAIL);
        }
    }
}