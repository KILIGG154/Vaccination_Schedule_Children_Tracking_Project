package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.Order;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.OrderStatus;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Payment;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.VaccineOrder;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.AppException;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.ErrorCode;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.Payment.PaymentRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.VaccineOrderRepo;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.PaymentService;
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

    @Autowired
    private VaccineOrderRepo vaccineOrderRepo;

    public Payment processStripePayment(int orderID, PaymentRequest request) {
        log.info("Get token: " + request.getToken());
        try {
            // Xử lý thanh toán qua Stripe
            Charge charge = stripeService.chargeCard(
                    request.getToken(),
                    request.getAmount(),
                    "vnd" // hoặc "USD" tùy vào loại tiền tệ bạn sử dụng
            );
            log.info("Charge status: " + charge.getPaid());
            // Nếu thanh toán thành công
            if (charge.getPaid()) {
                // Cập nhật request với thông tin từ Stripe
                request.setStatus(OrderStatus.COMPLETED);
                request.setPaymentMethod("MasterCard");

                // Sử dụng service hiện có để lưu thông tin thanh toán
                return paymentService.createPayment(orderID, request);
            } else {
                // Xử lý khi thanh toán thất bại
                request.setStatus(OrderStatus.FAIL);
                request.setPaymentMethod("MasterCard");
                return paymentService.createPayment(orderID, request);
            }
        } catch (StripeException e) {
            // Xử lý ngoại lệ từ Stripe
            throw new AppException(ErrorCode.PAYMENT_FAIL);
        }
    }
}