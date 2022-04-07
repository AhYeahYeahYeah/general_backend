package com.workflow.general_backend.mapper;

import com.workflow.general_backend.entity.Guidance;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GuidanceMapper {
    Guidance findById(String id);
}
