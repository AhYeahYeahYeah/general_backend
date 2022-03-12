package com.workflow.general_backend.mapper;

import com.workflow.general_backend.entity.Orders;
import com.workflow.general_backend.entity.Star;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface StarMapper {
    List<Star> findAll();

    List<Star> findById(String cid);

    int insert(Star star) throws DataAccessException;

    int update(Star star) throws DataAccessException;
}
