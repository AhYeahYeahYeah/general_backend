package com.workflow.general_backend.controller;


import com.workflow.general_backend.entity.Permission;
import com.workflow.general_backend.service.PermissionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/v1/entity/permission")
public class PermissionController {
    @Resource
    PermissionService permissionService;

    @GetMapping
    public List<Permission> findAll() {
        return permissionService.findAll();
    }

    @GetMapping("/{pid}")
    public List<Permission> findById(@PathVariable("pid") String pid) {
        return permissionService.findById(pid);
    }
}
