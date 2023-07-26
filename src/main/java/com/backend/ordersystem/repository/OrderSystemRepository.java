package com.backend.ordersystem.repository;

import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.backend.ordersystem.domain.OrderSystem;

public interface OrderSystemRepository extends JpaRepository<OrderSystem,Integer> {
    Page<OrderSystem> findAll(Pageable pageable);


    @Transactional
    @Lock(LockModeType.OPTIMISTIC)
    OrderSystem save(OrderSystem orderSystem);


    @Transactional
    @Modifying
    @Query("UPDATE OrderSystem o SET o.status=?1 WHERE o.orderid=?2")
    int updateStatus(String status, Integer id);
    @Transactional
    @Lock(LockModeType.OPTIMISTIC)
    OrderSystem findByOrderidAndStatus(Integer orderid, String status);
}
