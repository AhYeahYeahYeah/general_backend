package com.workflow.general_backend.mapper;

import com.workflow.general_backend.dto.CommonResult;
import com.workflow.general_backend.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface ProductMapper {

    List<Product> findAll();

    List<Product> findById(String pid);

    int insert(Product product) throws DataAccessException;

    int deleteById(String pid);

    int update(Product product) throws DataAccessException;
}
