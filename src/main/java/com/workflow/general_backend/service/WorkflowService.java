package com.workflow.general_backend.service;

import com.workflow.general_backend.dto.CommonResult;
import com.workflow.general_backend.dto.WorkflowDto;
import com.workflow.general_backend.entity.Workflow;

import java.util.List;

public interface WorkflowService {
    List<Workflow> findAll();

    List<Workflow> findById(String fid);

    CommonResult insert(WorkflowDto workflowDto);

    CommonResult deleteById(String fid);

    CommonResult update(WorkflowDto workflowDto);
}
