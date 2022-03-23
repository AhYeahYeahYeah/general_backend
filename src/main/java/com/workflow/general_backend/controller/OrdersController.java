package com.workflow.general_backend.controller;

import com.workflow.general_backend.dto.CommonResult;
import com.workflow.general_backend.dto.OrdersDto;
import com.workflow.general_backend.dto.OrdersVo;
import com.workflow.general_backend.entity.Orders;
import com.workflow.general_backend.entity.Product;
import com.workflow.general_backend.service.OrdersService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/v1/entity/orders")
public class OrdersController {
    @Resource
    OrdersService ordersService;
    @GetMapping
    public List<OrdersVo> findAll() {
        return ordersService.findAll();
    }

    @GetMapping("/dashboard")
    public List<OrdersVo> findAllForDash(){return ordersService.findAll();}

    @GetMapping("/dashboard/recent")
    public List<Orders> findRecent(){return ordersService.findRecent();}

    @GetMapping("/{oid}")
    public List<Orders> findById(@PathVariable("oid") String oid) {
        return ordersService.findById(oid);
    }

    @GetMapping("/getByCustomer/{cid}")
    public List<Orders> findByCid(@PathVariable("cid") String cid) {
        return ordersService.findByCid(cid);
    }

    @PostMapping
    public CommonResult insert(@RequestBody OrdersDto ordersDto) {
        return ordersService.insert(ordersDto);
    }

    @PutMapping
    public CommonResult update(@RequestBody Orders orders) { return ordersService.update(orders); }
}
