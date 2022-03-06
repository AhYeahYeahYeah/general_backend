package com.workflow.general_backend.service;

import com.workflow.general_backend.entity.ServiceInfo;

import java.util.List;

public interface ServiceInfoService {

    List<ServiceInfo> findAll();

    List<ServiceInfo> findById(String sid);
}
