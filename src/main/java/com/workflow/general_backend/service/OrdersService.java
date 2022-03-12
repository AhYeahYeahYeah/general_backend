package com.workflow.general_backend.service;

import com.workflow.general_backend.dto.CommonResult;
import com.workflow.general_backend.entity.Orders;

import java.util.List;

public interface OrdersService {
    List<Orders> findAll();

    List<Orders> findById(String oid);

    List<Orders> findByCid(String cid);

    CommonResult insert(Orders orders);

    CommonResult update(Orders orders);
}
