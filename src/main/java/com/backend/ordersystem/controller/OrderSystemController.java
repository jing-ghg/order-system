package com.backend.ordersystem.controller;

import java.util.*;

import com.backend.ordersystem.domain.CustomResponseStatusException;
import com.backend.ordersystem.DTO.OrderRequestDTO;
import com.backend.ordersystem.DTO.OrderResponseDTO;
import com.backend.ordersystem.service.OrderService;
import com.backend.ordersystem.domain.OrderSystem;
import com.backend.ordersystem.repository.OrderSystemRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/orders")
public class OrderSystemController {


    @Autowired
    private OrderSystemRepository orderSystemRepository;

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getOrders(@RequestParam(value = "page") @NotNull @Min(1) Integer page, @RequestParam(name = "limit") @NotNull int limit)

    {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrderList(page-1, limit));
    }
    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody @Valid OrderRequestDTO orderRequestDTO) {
        String[] origin = orderRequestDTO.getOrigin();
        String[] destination = orderRequestDTO.getDestination();

        int distance = orderService.calculateDistance(origin, destination);

        OrderSystem orderSystem = new OrderSystem(Float.valueOf(origin[0]), Float.valueOf(origin[1]), Float.valueOf(destination[0]), Float.valueOf(destination[1]), distance,"UNASSIGNED");
        OrderSystem response = orderService.createOrder(orderSystem);



        return ResponseEntity.status(HttpStatus.CREATED).body(new OrderResponseDTO(response));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Map<String,String>> takeOrder(@PathVariable @NotNull Integer id, @RequestBody @NotBlank String status) {

        String statusResponse = orderService.takeOrder(status, id);
        if (statusResponse.equals("SUCCESS")){
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("status", statusResponse);
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        }

        throw new CustomResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Order is not taken");

    }

}
