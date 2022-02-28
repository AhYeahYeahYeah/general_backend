package com.workflow.general_backend.mapper;

import com.workflow.general_backend.entity.Customer;
import com.workflow.general_backend.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerMapperTest {
    @Resource
    CustomerMapper customerMapper;
    @Test
    void findCustomerByAccount() {
        Customer c=customerMapper.findCustomerByAccount("123");
        System.out.println(c.getAccount()+" "+c.getPassword());
    }

    @Test
    void addCustomer() {
    }
}