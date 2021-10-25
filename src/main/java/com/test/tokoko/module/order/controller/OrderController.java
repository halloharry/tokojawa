package com.test.tokoko.module.order.controller;

import com.test.tokoko.common.payload.BaseResponse;
import com.test.tokoko.module.order.payload.OrderRequest;
import com.test.tokoko.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/input-order")
    public BaseResponse inputOrder(@RequestBody OrderRequest orderRequest){
        System.out.println(orderRequest);
        return orderService.inputOrder(orderRequest);
    }

    @PostMapping("/update-order/{id}")
    public BaseResponse updateProduct(@PathVariable("id") Long id, @RequestBody OrderRequest orderRequest){
        System.out.println(orderRequest);
        return orderService.updateOrder(orderRequest, id);
    }

    @PostMapping("/cancel/{id}")
    public BaseResponse cancelOrder(@PathVariable("id") Long id){
        return orderService.cancelOrder(id);
    }

}
