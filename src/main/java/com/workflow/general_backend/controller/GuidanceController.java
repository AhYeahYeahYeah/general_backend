package com.workflow.general_backend.controller;

import com.workflow.general_backend.entity.Customer;
import com.workflow.general_backend.entity.Guidance;
import com.workflow.general_backend.service.GuidanceService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/v1/entity/guidance")
public class GuidanceController {
    @Resource
    GuidanceService guidanceService;
    @GetMapping("/{id}")
    public Guidance findById(@PathVariable("id") String id) {
        return guidanceService.findById(id);
    }
}
