package com.workflow.general_backend.service.Impl;

import com.workflow.general_backend.dto.CustomerDto;
import com.workflow.general_backend.entity.Customer;
import com.workflow.general_backend.mapper.CustomerMapper;
import com.workflow.general_backend.service.CustomerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class CustomerServiceImpl implements CustomerService {
    @Resource
    CustomerMapper customerMapper;
    @Override
    public CustomerDto clogin(Customer customer) {

        return null;
    }
}
