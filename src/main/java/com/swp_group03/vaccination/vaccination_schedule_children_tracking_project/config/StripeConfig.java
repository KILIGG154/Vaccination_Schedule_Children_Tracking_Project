package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.config;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class StripeConfig {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @PostConstruct
    public void init() {
        log.info("Initializing Stripe with API key: {}...", stripeApiKey.substring(0, 8) + "**********");
        Stripe.apiKey = stripeApiKey;
    }
}