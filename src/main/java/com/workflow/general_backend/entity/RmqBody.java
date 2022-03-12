package com.workflow.general_backend.entity;

import com.workflow.general_backend.dto.OrdersDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RmqBody {
    private OrdersDto ordersDto;
    private int number;

    public OrdersDto getOrdersDto() {
        return ordersDto;
    }

    public void setOrders(OrdersDto ordersDto) {
        this.ordersDto = ordersDto;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
