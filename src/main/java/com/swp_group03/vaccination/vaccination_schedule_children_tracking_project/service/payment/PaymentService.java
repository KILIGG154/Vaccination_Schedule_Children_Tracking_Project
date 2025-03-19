package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.payment;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Payment;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.VaccineOrder;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.AppException;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.ErrorCode;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.payment.PaymentRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.PaymentRepo;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.VaccineOrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {



    @Autowired
    private PaymentRepo paymentRepo;


    @Autowired
    private VaccineOrderRepo vaccineOrderRepo;


    public Payment createPayment(int orderID ,PaymentRequest request) {
        VaccineOrder orders = vaccineOrderRepo.findById(orderID).orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY));

        Payment payment = new Payment();
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setStatus(request.getStatus());
        payment.setVaccineOrder(orders);
        vaccineOrderRepo.save(orders);
        return paymentRepo.save(payment);
    }


    public List<Payment> getAllPayment(){
        return paymentRepo.findAll();
    }


}
