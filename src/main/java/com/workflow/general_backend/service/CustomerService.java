package com.workflow.general_backend.service;

import com.workflow.general_backend.dto.CommonResult;
import com.workflow.general_backend.dto.CustomerDto;
import com.workflow.general_backend.entity.Customer;
import org.springframework.stereotype.Service;


public interface CustomerService {
    CustomerDto clogin(Customer customer);

    CommonResult cregister(Customer customer);
}
