package com.workflow.general_backend.mapper;

import com.workflow.general_backend.entity.Whitelist;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface WhiteListMapper {
    List<Whitelist> findAll();

    List<Whitelist> findById(String wid);

    int insert(Whitelist whitelist) throws DataAccessException;

    int deleteById(String wid);

    int update(Whitelist whitelist) throws DataAccessException;
}
