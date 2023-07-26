package com.backend.ordersystem.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class OrderRequestDTO
{
    @NotNull(message = "Origin cannot be null")
    @NotEmpty(message = "Origin cannot be empty")
    @Size(min = 2, max = 2, message = "There must be two values for origin")
    private String[] origin;

    @NotNull(message = "Destination cannot be null")
    @NotEmpty(message = "Destination cannot be empty")
    @Size(min = 2, max = 2, message = "There must be two values for destination")
    private String[] destination;

    public String[] getOrigin() {
        return origin;
    }

    public String[] getDestination() {
        return destination;
    }

    public void setOrigin(String[] origin) {
        this.origin = origin;
    }
    public void setDestination(String[] destination) {
        this.destination = destination;
    }



}
