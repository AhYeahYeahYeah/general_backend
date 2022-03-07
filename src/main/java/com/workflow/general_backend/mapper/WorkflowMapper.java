package com.workflow.general_backend.mapper;

import com.workflow.general_backend.entity.Product;
import com.workflow.general_backend.entity.Workflow;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface WorkflowMapper {
    List<Workflow> findAll();

    List<Workflow> findById(String fid);

    int insert(Workflow workflow);

    int deleteById(String fid);

    int update(Workflow workflow);
}
