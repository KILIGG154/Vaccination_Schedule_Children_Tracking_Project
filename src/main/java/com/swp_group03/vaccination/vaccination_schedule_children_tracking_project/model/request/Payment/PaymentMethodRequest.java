package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.Payment;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentMethodRequest {

    private String methodName;

    public PaymentMethodRequest() {
    }

    public PaymentMethodRequest(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
