package com.workflow.general_backend.mapper;

import com.workflow.general_backend.entity.ServiceInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface ServiceInfoMapper {
    List<ServiceInfo> findAll();

    List<ServiceInfo> findById(String sid);
}
