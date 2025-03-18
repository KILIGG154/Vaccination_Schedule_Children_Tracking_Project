package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.Order;

import com.stripe.exception.StripeException;
import com.stripe.exception.ApiConnectionException;
import com.stripe.model.Charge;
import com.stripe.model.PaymentIntent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class StripeService {

    public Charge chargeCard(String token, double amount, String currency) throws StripeException {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", (int)(amount * 100)); // Stripe requires amount in cents
        chargeParams.put("currency", currency);
        chargeParams.put("source", token);
        chargeParams.put("description", "Thanh toán vắc-xin");

        return Charge.create(chargeParams);
    }

    public PaymentIntent createPaymentIntent(double amount) throws StripeException {
        Map<String, Object> params = new HashMap<>();
        params.put("amount", (int)(amount * 100)); // Stripe requires amount in cents
        params.put("currency", "usd");
        params.put("payment_method_types", new String[]{"card"});
        params.put("description", "Thanh toán vắc-xin");

        return PaymentIntent.create(params);
    }

    public PaymentIntent confirmPaymentIntent(String paymentIntentId) throws StripeException {
        // PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
        // return paymentIntent.confirm();
        log.info("Retrieving payment intent with ID: {}", paymentIntentId);

        try {
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
            log.info("Retrieved payment intent. Current status: {}", paymentIntent.getStatus());

            // Check if payment intent is already in a terminal state
            if ("succeeded".equals(paymentIntent.getStatus())) {
                log.info("Payment intent already succeeded. No need to confirm again.");
                return paymentIntent;
            }

            if ("canceled".equals(paymentIntent.getStatus())) {
                log.error("Cannot confirm a canceled payment intent.");
                throw new ApiConnectionException("Cannot confirm a canceled payment intent");
            }

            // If payment intent requires confirmation and is in a state that can be confirmed
            if (!paymentIntent.getStatus().equals("requires_payment_method") &&
                    !paymentIntent.getStatus().equals("requires_confirmation")) {
                log.error("Payment intent is in state {} which cannot be confirmed", paymentIntent.getStatus());
                throw new ApiConnectionException("Payment intent is in an unexpected state: " + paymentIntent.getStatus());
            }

            log.info("Confirming payment intent: {}", paymentIntentId);
            Map<String, Object> confirmParams = new HashMap<>();
            // For 3D Secure support if needed
            // confirmParams.put("return_url", "https://your-website.com/return");

            PaymentIntent confirmedIntent = paymentIntent.confirm(confirmParams);
            log.info("Payment intent confirmed. New status: {}", confirmedIntent.getStatus());

            return confirmedIntent;
        } catch (StripeException e) {
            log.error("Stripe exception during payment intent confirmation: {}, code: {}",
                    e.getMessage(), e.getCode());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error during payment intent confirmation: {}", e.getMessage());
            throw new ApiConnectionException("Unexpected error during payment confirmation");
        }
    }

    public PaymentIntent cancelPaymentIntent(String paymentIntentId) throws StripeException {
        PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
        return paymentIntent.cancel();
    }
}