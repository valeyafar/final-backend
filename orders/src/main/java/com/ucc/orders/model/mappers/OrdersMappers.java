package com.ucc.orders.model.mappers;

import com.ucc.orders.model.dto.OrderDTO;
import com.ucc.orders.model.dto.OrderInfoDTO;
import com.ucc.orders.model.entities.Order;
import org.springframework.stereotype.Component;

@Component
public class OrdersMappers {

    public Order ordersDTOToEntity(OrderDTO dto) {
        Order orderEntity = new Order();
        orderEntity.setProductID(dto.getProductID());
        orderEntity.setQuantity(dto.getQuantity());

        return orderEntity;
    }

    public OrderInfoDTO toDTO(Order order) {
        return new OrderInfoDTO(
                order.getId(),
                order.getProductID(),
                order.getQuantity(),
                order.getCreationDate()
        );
    }

}
