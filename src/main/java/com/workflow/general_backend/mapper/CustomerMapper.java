package com.workflow.general_backend.mapper;

import com.workflow.general_backend.entity.Customer;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerMapper {
    Customer findCustomerByAccount(String account);
    int addCustomer(Customer customer);
}
