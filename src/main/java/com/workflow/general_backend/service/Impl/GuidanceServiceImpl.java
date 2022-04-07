package com.workflow.general_backend.service.Impl;

import com.workflow.general_backend.entity.Guidance;
import com.workflow.general_backend.mapper.GuidanceMapper;
import com.workflow.general_backend.service.GuidanceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class GuidanceServiceImpl implements GuidanceService {
    @Resource
    GuidanceMapper guidanceMapper;
    @Override
    public Guidance findById(String id) {
        return guidanceMapper.findById(id.substring(7));
    }
}
