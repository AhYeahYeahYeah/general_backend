package com.workflow.general_backend.controller;

import com.workflow.general_backend.dto.CommonResult;
import com.workflow.general_backend.dto.WorkflowDto;

import com.workflow.general_backend.entity.Workflow;

import com.workflow.general_backend.service.WorkflowService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/v1/entity/workflow")
public class WorkflowController {
    @Resource
    WorkflowService workflowService;

    @GetMapping
    public List<Workflow> findAll() {
        return workflowService.findAll();
    }

    @GetMapping("/dashboard")
    public List<Workflow> findAllForDash() {
        return workflowService.findAll();
    }

    @GetMapping("/{fid}")
    public List<Workflow> findById(@PathVariable("fid") String fid) {
        return workflowService.findById(fid);
    }

    @PostMapping
    public CommonResult insert(@RequestBody WorkflowDto workflowDto) {
        return workflowService.insert(workflowDto);
    }

    @DeleteMapping("/{fid}")
    public CommonResult deleteById(@PathVariable("fid") String fid) {
        return workflowService.deleteById(fid);
    }

    @PutMapping
    public CommonResult update(@RequestBody WorkflowDto workflowDto) {
        return workflowService.update(workflowDto);
    }

}
