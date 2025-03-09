package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Payment;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Payment_Method;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.mapper.PaymentMapper;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.mapper.PaymentMethodMapper;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.Payment.PaymentMethodRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.Payment.PaymentRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.PaymentMethodRepo;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentMethodRepo paymentMethodRepo;

    @Autowired
    private PaymentRepo paymentRepo;

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private PaymentMethodMapper paymentMethodMapper;

    public Payment_Method createMethod(PaymentMethodRequest paymentMethodRequest) {
      Payment_Method paymentMethod = new Payment_Method();
        paymentMethod.setMethodName(paymentMethodRequest.getMethodName());
        return paymentMethodRepo.save(paymentMethod);
    }

    public Payment createPayment(PaymentRequest request) {
        Payment payment = new Payment();
        payment.setPaymentDate(request.getPaymentDate());
        payment.setStatus(request.isStatus());
        payment.setPaymentMethod(request.getPaymentMethod());
        return paymentRepo.save(payment);
    }

    public List<Payment_Method> getAll(){
        return paymentMethodRepo.findAll();
    }


    public List<Payment> getAllPayment(){
        return paymentRepo.findAll();
    }

    public Payment_Method getMethodByPayment(int id){
        Payment payment = paymentRepo.findById(id).orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));

        return payment.getPaymentMethod();

    }

    public List<Payment> getPaymentByMethod(int id){
        Payment_Method paymentMethod = paymentMethodRepo.findById(id).orElseThrow(() -> new RuntimeException("Payment Method not found with id: " + id));
        return paymentRepo.findByPaymentMethod(paymentMethod);
    }
}
