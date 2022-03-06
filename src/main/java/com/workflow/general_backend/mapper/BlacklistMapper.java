package com.workflow.general_backend.mapper;

import com.workflow.general_backend.dto.CommonResult;
import com.workflow.general_backend.entity.Blacklist;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface BlacklistMapper {
    List<Blacklist> findAll();

    List<Blacklist> findById(String bid);

    int insert(Blacklist blacklist) throws DataAccessException;

    int deleteById(String bid);

    int update(Blacklist blacklist) throws DataAccessException;
}
