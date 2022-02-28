package com.workflow.general_backend.mapper;

import com.workflow.general_backend.entity.Customer;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerMapper {
    public Customer findCustomerByAccount(String account);
    public int addAdmin(Customer customer);
}
