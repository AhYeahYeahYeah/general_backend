package com.workflow.general_backend.mapper;

import com.workflow.general_backend.entity.Log;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LogMapper {

    List<Log> findAll();

    List<Log> findById(String lid);

    int deleteById(String lid);
}
