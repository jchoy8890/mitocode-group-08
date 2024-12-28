package com.mitocode.microservices.mitocodeworkflowcoreography.controller;


import com.mitocode.microservices.mitocodeworkflowcoreography.request.OrderRequest;
import com.mitocode.microservices.mitocodeworkflowcoreography.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class WorkflowController {

    private final OrderService orderService;

    @GetMapping("/start/{orderId}")
    public String createOrder(@PathVariable("orderId") String orderId) {
        return orderService.createOrder(orderId);
    }


    @PatchMapping("/update")
    public String updateOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.updateOrder(orderRequest);
    }

    @GetMapping("/update/{productId}/{quantity}/{workflowId}")
    public String updateProduct(@PathVariable("productId") String productId,
                                @PathVariable("quantity") Integer quantity,
                                @PathVariable("workflowId") String workflowId) {
        return orderService.updateProduct(productId, quantity, workflowId);
    }

    @GetMapping("/update/{orderId}/{workflowId}")
    public String confirmOrder(@PathVariable("orderId") String orderId,
                               @PathVariable("workflowId") String workflowId) {
        return orderService.acceptOrder(orderId, workflowId);
    }

    @GetMapping("/cancel/{orderId}/{workflowId}")
    public String cancelProcess(@PathVariable("orderId") String orderId,
                               @PathVariable("workflowId") String workflowId) {
        return orderService.cancelProcess(orderId, workflowId);
    }

}
