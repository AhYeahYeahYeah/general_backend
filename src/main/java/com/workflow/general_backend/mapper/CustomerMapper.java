package com.workflow.general_backend.mapper;

import com.workflow.general_backend.entity.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface CustomerMapper {
    Customer findCustomerByAccount(String account);

    List<Customer> findAll();

    List<Customer> findById(String cid);

    int insert(Customer customer) throws DataAccessException;

    int deleteById(String cid);

    int update(Customer customer) throws DataAccessException;
}
