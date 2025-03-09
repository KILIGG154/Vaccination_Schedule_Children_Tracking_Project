package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.api;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Payment;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Payment_Method;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.Payment.PaymentMethodRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.Payment.PaymentRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.ApiResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/addMethod")
    public ApiResponse addPaymentMethod(@RequestBody PaymentMethodRequest request) {
        Payment_Method method = paymentService.createMethod(request);
        return ApiResponse.builder().code(201).message("Successfully added payment method").build();

    }

    @PostMapping("/addPayment")
    public ApiResponse addPayment(@RequestBody PaymentRequest request) {
        Payment payments = paymentService.createPayment(request);
        return ApiResponse.builder().code(201).message("Successfully added payment method").build();
    }

    @GetMapping("/method/{methodID}")
    public ResponseEntity<Payment_Method> getMethod(@PathVariable int methodID) {
        Payment_Method method = paymentService.getMethodByPayment(methodID);
        return ResponseEntity.ok(method);
    }

    @GetMapping("/getall/{methodID}")
    public ResponseEntity<List<Payment>> findbyPaymentMewthod(@PathVariable int methodID) {
        return ResponseEntity.ok(paymentService.getPaymentByMethod(methodID));
    }

}
