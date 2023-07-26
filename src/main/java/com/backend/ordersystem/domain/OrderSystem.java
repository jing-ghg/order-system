package com.backend.ordersystem.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


@Entity // This tells Hibernate to make a table out of this class
@Table(name = "orders")
public class OrderSystem {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer orderid;

    @Version
    @Column(name = "version", columnDefinition = "INTEGER DEFAULT 0", nullable = false)
    private Integer version;
    public OrderSystem() {}


    public OrderSystem(Float startLatitude, Float startLongitude, Float endLatitude, Float endLongitude, Integer distance, String status) {
        this.distance = distance;
        this.status = status;
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.endLatitude = endLatitude;
        this.endLongitude = endLongitude;
    }

    private Integer distance;

    private Float startLatitude;

    private Float startLongitude;

    private Float endLatitude;

    private Float endLongitude;

    private String status;

    public Integer getOrderid() {
        return orderid;
    }
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public Float getStartlatitude() {
        return startLatitude;
    }

    public void setStartlatitude(Float startLatitude) {
        this.startLatitude = startLatitude;
    }

    public Float getStartlongitude() {
        return startLongitude;
    }

    public void setStartlongitude(Float startLongitude) {
        this.startLongitude = startLongitude;
    }

    public Float getEndlatitude() {
        return endLatitude;
    }

    public void setEndlatitude(Float endLatitude) {
        this.endLatitude = endLatitude;
    }

    public Float getEndlongitude() {
        return endLongitude;
    }

    public void setEndlongitude(Float endLongitude) {
        this.endLongitude = endLongitude;
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
