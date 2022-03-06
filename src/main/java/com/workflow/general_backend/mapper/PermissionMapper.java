package com.workflow.general_backend.mapper;

import com.workflow.general_backend.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PermissionMapper {

    List<Permission> findAll();

    List<Permission> findById(String pid);
}
