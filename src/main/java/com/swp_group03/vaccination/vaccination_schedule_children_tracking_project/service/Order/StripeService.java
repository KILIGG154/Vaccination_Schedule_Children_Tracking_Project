package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.Order;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.PaymentIntent;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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

    public PaymentIntent createPaymentIntent(double amount, String currency) throws StripeException {
        Map<String, Object> params = new HashMap<>();
        params.put("amount", (int)(amount * 100));
        params.put("currency", currency);
        params.put("payment_method_types", new String[]{"card"});

        return PaymentIntent.create(params);
    }
}