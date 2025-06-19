package com.ucc.orders.controller;

import com.ucc.orders.model.dto.OrderDTO;
import com.ucc.orders.model.dto.OrderInfoDTO;
import com.ucc.orders.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderInfoDTO> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        OrderInfoDTO newOrder = orderService.createOrder(orderDTO);
        return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
    }


    @GetMapping("/info")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderInfoDTO> getOrdersDTO(){
        return orderService.getOrdersDTO();
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderInfoDTO> updateOrder(@PathVariable Long id, @RequestBody OrderDTO orderDTO) {
        OrderInfoDTO updatedOrder = orderService.updateOrder(id, orderDTO);
        return ResponseEntity.ok(updatedOrder);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderInfoDTO getOrderById(@PathVariable Long id){
        return orderService.getOrderInfoById(id);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteOrder(@PathVariable Long id) {
        return orderService.deleteOrder(id);
    }
}
