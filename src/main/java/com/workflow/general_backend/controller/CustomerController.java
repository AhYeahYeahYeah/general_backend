package com.workflow.general_backend.controller;

import com.workflow.general_backend.service.CustomerService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@CrossOrigin
@RestController
@RequestMapping("/v1/entity/customer")
public class CustomerController {
    @Resource
    CustomerService customerService;

//    @GetMapping
//    public Integer findAll(){
//        return 10086;
//    }

}
