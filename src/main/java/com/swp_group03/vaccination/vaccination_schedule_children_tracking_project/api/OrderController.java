package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.api;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.VaccineOrder;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.order.VaccineOrderRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.order.VaccineaOrderDetaIlRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.ApiResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.order.VaccineOrderDTO;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.Order.VaccineOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private VaccineOrderService vaccineOrderService;

    @PostMapping("/{bookingID}/create")
    public ApiResponse<VaccineOrderDTO> createOrder(@PathVariable int bookingID, @RequestBody VaccineOrderRequest request) {
//        VaccineOrderDTO order = vaccineOrderService.createVaccineOrder(bookingID,request);
        VaccineOrderDTO order = vaccineOrderService.createVaccineOrder(bookingID,request);
//        return ApiResponse.builder().code(201).message("Successfully created order").result(order).build();
        return ApiResponse.<VaccineOrderDTO>builder()
                .code(201)
                .message("Successfully created order")
                .result(order)
                .build();
    }

    @PostMapping("/{orderID}/addDetail/{vaccineID}")
    public ApiResponse addDetail(@PathVariable int orderID, @PathVariable int vaccineID, @RequestBody VaccineaOrderDetaIlRequest request) {
        return ApiResponse.builder()
                .code(201)
                .message("Successfully added detail")
                .result(vaccineOrderService.createVaccineOrderDetail(orderID, vaccineID, request))
                .build();
    }

    @GetMapping("/{bookingID}/all")
    public ApiResponse getAllOrderByBookingId(@PathVariable int bookingID) {
        List<VaccineOrderDTO> dto = vaccineOrderService.getAllOrderByBookingId(bookingID);
        return ApiResponse.builder()
                .code(200)
                .message("Successfully get all order")
                .result(dto)
                .build();
    }

    @GetMapping("/{orderID}/addCombo/{comboID}")
    public ApiResponse addCombo(@PathVariable int orderID, @PathVariable int comboID) {
        return ApiResponse.builder()
                .code(201)
                .message("Successfully added combo")
                .result(vaccineOrderService.createOrderVaccineCombo(orderID, comboID))
                .build();
    }

    @PutMapping("/{orderID}/done")
    public ApiResponse doneOrder(@PathVariable int orderID) {
        return ApiResponse.builder()
                .code(200)
                .message("Successfully done order")
                .result(vaccineOrderService.doneOrder(orderID))
                .build();
    }

    @PutMapping("/{orderID}/rejected")
    public ApiResponse rejectedOrder(@PathVariable int orderID) {
        return ApiResponse.builder()
                .code(200)
                .message("Successfully done order")
                .result(vaccineOrderService.rejectOrder(orderID))
                .build();
    }


}
