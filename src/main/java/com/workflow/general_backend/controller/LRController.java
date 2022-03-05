package com.workflow.general_backend.controller;

import com.workflow.general_backend.dto.AdminDto;
import com.workflow.general_backend.dto.CommonResult;
import com.workflow.general_backend.dto.CustomerDto;
import com.workflow.general_backend.entity.Admin;
import com.workflow.general_backend.entity.Customer;
import com.workflow.general_backend.service.AdminService;
import com.workflow.general_backend.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@CrossOrigin
@RestController
@RequestMapping("/v1/auth")
public class LRController {

    @Resource
    CustomerService customerService;
    @Resource
    AdminService adminService;

    @PostMapping("/clogin")
    public CustomerDto clogin(@RequestBody Customer customer) {
        return customerService.clogin(customer);
    }

    @PostMapping("/alogin")
    public AdminDto alogin(@RequestBody Admin admin) {
        return adminService.alogin(admin);
    }

    @PostMapping("/cregister")
    public CommonResult cregister(@RequestBody Customer customer) {
        return customerService.cregister(customer);
    }

    @PostMapping("/aregister")
    public CommonResult aregister(@RequestBody Admin admin) {
        return adminService.aregister(admin);
    }


}
