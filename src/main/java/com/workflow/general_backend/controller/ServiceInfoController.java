package com.workflow.general_backend.controller;

import com.workflow.general_backend.entity.Product;
import com.workflow.general_backend.entity.ServiceInfo;
import com.workflow.general_backend.service.ServiceInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/v1/entity/serviceinfo")
public class ServiceInfoController {
    @Resource
    ServiceInfoService serviceInfoService;

    @GetMapping
    public List<ServiceInfo> findAll() {
        return serviceInfoService.findAll();
    }

    @GetMapping("/{sid}")
    public List<ServiceInfo> findById(@PathVariable("sid") String sid) {
        return serviceInfoService.findById(sid);
    }

}
