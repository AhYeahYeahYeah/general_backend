package com.workflow.general_backend.service.Impl;

import com.workflow.general_backend.entity.Permission;
import com.workflow.general_backend.mapper.PermissionMapper;
import com.workflow.general_backend.service.PermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Resource
    PermissionMapper permissionMapper;


    @Override
    public List<Permission> findAll() {
        return permissionMapper.findAll();
    }

    @Override
    public List<Permission> findById(String pid) {
        return permissionMapper.findById(pid);
    }
}
