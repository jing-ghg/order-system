package com.backend.ordersystem.mapper;

import com.backend.ordersystem.DTO.OrderResponseDTO;
import com.backend.ordersystem.domain.OrderSystem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;
@Mapper(componentModel = "spring")
public interface OrderSystemMapper {
    @Mapping(target = "id", source = "orderid")
    @Mapping(target = "distance", source = "distance")
    @Mapping(target = "status", source = "status")
    OrderResponseDTO toDto(OrderSystem orderSystem);

    List<OrderResponseDTO> toDtoList(List<OrderSystem> orderSystemList);
}
