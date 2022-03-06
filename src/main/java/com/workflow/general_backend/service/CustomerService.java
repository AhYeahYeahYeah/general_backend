package com.workflow.general_backend.service;

import com.workflow.general_backend.dto.CommonResult;
import com.workflow.general_backend.dto.CustomerDto;
import com.workflow.general_backend.entity.Customer;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CustomerService {
    CustomerDto clogin(Customer customer);

    CommonResult cregister(Customer customer);

    List<Customer> findAll();

    List<Customer> findById(String cid);

    CommonResult insert(Customer customer);

    CommonResult deleteById(String cid);

    CommonResult update(Customer customer);
}
