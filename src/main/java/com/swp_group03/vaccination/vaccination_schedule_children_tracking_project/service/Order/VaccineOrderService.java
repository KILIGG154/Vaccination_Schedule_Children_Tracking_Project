package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.Order;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Booking;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Vaccine;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.VaccineOrder;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.VaccineOrderDetail;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.AppException;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.ErrorCode;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.order.VaccineOrderRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.order.VaccineaOrderDetaIlRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.order.VaccineOrderDTO;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.BookingRepo;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.VaccineOrderRepo;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.VaccineRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class VaccineOrderService {

    @Autowired
    private VaccineOrderRepo vaccineOrderRepo;

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private VaccineRepo vaccineRepo;

    public VaccineOrder createVaccineOrder(int bookingID , VaccineOrderRequest request) {
        Booking booking = bookingRepo.findById(bookingID).orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY));

        VaccineOrder order = new VaccineOrder();
        log.info("Order date: {}", request.getOrderDate());
        order.setDateOrderVaccine(request.getOrderDate());
        booking.addVaccineOrder(order);
        bookingRepo.save(booking);
        return vaccineOrderRepo.save(order);
    }

    public VaccineOrderDetail createVaccineOrderDetail(int orderID, int vaccineId ,VaccineaOrderDetaIlRequest request) {
        VaccineOrder order = vaccineOrderRepo.findById(orderID).orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY));

        Vaccine vaccine = vaccineRepo.findById(vaccineId).orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY));

        VaccineOrderDetail detail = new VaccineOrderDetail();
        detail.setQunatity(request.getQuantity());
        detail.setTotalPrice(vaccine.getSalePrice() * request.getQuantity());
        detail.setVaccine(vaccine);
        order.addVaccineOrderDetail(detail);
        vaccineOrderRepo.save(order);
        return detail;
    }
}
