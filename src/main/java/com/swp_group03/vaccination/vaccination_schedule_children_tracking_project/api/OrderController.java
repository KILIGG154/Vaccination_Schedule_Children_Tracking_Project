package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.api;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.VaccineOrder;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.order.VaccineOrderRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.order.VaccineaOrderDetaIlRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.ApiResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.order.VaccineOrderDTO;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.Order.VaccineOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


}
