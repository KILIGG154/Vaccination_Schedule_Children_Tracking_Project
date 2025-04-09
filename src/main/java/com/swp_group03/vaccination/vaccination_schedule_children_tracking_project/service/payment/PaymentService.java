package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.payment;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.OrderStatus;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Payment;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.VaccineOrder;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.VaccineOrderDetail;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.vaccine.Vaccine;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.AppException;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.ErrorCode;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.payment.PaymentRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.PaymentRepo;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.VaccineOrderRepo;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.VaccineRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PaymentService {



    @Autowired
    private PaymentRepo paymentRepo;


    @Autowired
    private VaccineOrderRepo vaccineOrderRepo;

    @Autowired
    private VaccineRepo vaccineRepo;


    public Payment createPayment(int orderID, PaymentRequest request) {
        VaccineOrder orders = vaccineOrderRepo.findById(orderID).orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY));
    
        Payment payment = new Payment();
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setStatus(request.getStatus());
        
        // Save the payment first
        payment = paymentRepo.save(payment);
        orders.setStatus(OrderStatus.DONE);

        if(orders.getStatus() == OrderStatus.DONE) {
            Set<VaccineOrderDetail> details = orders.getVaccineOrderDetails();
            for (VaccineOrderDetail detail : details) {
                Vaccine vaccine = detail.getVaccine();
                int stockQuantity = vaccine.getQuantity();
                int curr = detail.getQunatity();
                if(curr > stockQuantity){
                    throw new AppException(ErrorCode.INVALID_KEY);
                }
                vaccine.setQuantity(stockQuantity - curr);
                vaccineRepo.save(vaccine);
            }
        }
        // Then set the relationship
        orders.setPayment(payment);
        payment.setVaccineOrder(orders);
        
        // Save the order
        vaccineOrderRepo.save(orders);
        
        return payment;
    }


    public List<Payment> getAllPayment(){
        return paymentRepo.findAll();
    }
//
//    public void devideVaccineEntity(int id){
//        VaccineOrder orders = vaccineOrderRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY));
//        Set<VaccineOrderDetail> details = orders.getVaccineOrderDetails();
//
//        for (VaccineOrderDetail detail : details) {
//            Vaccine vaccine = detail.getVaccine();
//            int orderQuantity = detail.getVaccine().getQuantity();
//            int curr = detail.getQunatity();
//            if(curr > orderQuantity){
//                throw new AppException(ErrorCode.INVALID_KEY);
//            }
//            vaccine.setQuantity(vaccine.getQuantity() - orderQuantity);
//            vaccineRepo.save(vaccine);
//        }
//
//        }


}
