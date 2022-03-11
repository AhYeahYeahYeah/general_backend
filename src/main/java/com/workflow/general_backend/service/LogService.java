package com.workflow.general_backend.service;

import com.workflow.general_backend.dto.CommonResult;
import com.workflow.general_backend.entity.Log;

import java.util.List;

public interface LogService {
    List<Log> findAll();

    List<Log> findById(String lid);

    CommonResult deleteById(String lid);
}
