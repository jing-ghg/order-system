package com.backend.ordersystem.DTO;

import com.backend.ordersystem.domain.OrderSystem;

public class OrderResponseDTO {

    private Integer id;
    private Integer distance;
    private String status;

    public OrderResponseDTO() {
    }

    public OrderResponseDTO(String status, Integer id) {
        this.status = status;
        this.id = id;
    }
    public OrderResponseDTO(OrderSystem orderSystem) {
        this.id = orderSystem.getOrderid();
        this.distance = orderSystem.getDistance();
        this.status = orderSystem.getStatus();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
