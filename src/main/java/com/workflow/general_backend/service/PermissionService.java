package com.workflow.general_backend.service;

import com.workflow.general_backend.entity.Permission;

import java.util.List;

public interface PermissionService {
    List<Permission> findAll();

    List<Permission> findById(String pid);
}
