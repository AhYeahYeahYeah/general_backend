package com.workflow.general_backend.service;

import com.workflow.general_backend.dto.CommonResult;
import com.workflow.general_backend.dto.OrdersDto;
import com.workflow.general_backend.entity.Orders;

import java.util.List;

public interface OrdersService {
    List<Orders> findAll();

    List<Orders> findRecent();

    List<Orders> findById(String oid);

    List<Orders> findByCid(String cid);

    CommonResult insert(OrdersDto ordersDto);

    CommonResult update(Orders orders);
}
