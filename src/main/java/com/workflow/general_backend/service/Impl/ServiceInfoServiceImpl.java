package com.workflow.general_backend.service.Impl;

import com.workflow.general_backend.entity.ServiceInfo;
import com.workflow.general_backend.mapper.ServiceInfoMapper;
import com.workflow.general_backend.service.ServiceInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ServiceInfoServiceImpl implements ServiceInfoService {
    @Resource
    ServiceInfoMapper serviceInfoMapper;

    @Override
    public List<ServiceInfo> findAll() {
        return serviceInfoMapper.findAll();
    }

    @Override
    public List<ServiceInfo> findById(String sid) {
        return serviceInfoMapper.findById(sid);
    }
}
