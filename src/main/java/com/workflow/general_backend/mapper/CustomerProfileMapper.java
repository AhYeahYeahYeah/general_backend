package com.workflow.general_backend.mapper;

import com.workflow.general_backend.entity.CustomerProfile;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface CustomerProfileMapper {

    List<CustomerProfile> findAll();

    List<CustomerProfile> findById(String cid);

    int insert(CustomerProfile customerProfile) throws DataAccessException;

    int deleteById(String cid);

    int update(CustomerProfile customerProfile) throws DataAccessException;
}
