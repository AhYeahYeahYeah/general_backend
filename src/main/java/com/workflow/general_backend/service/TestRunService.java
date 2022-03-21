package com.workflow.general_backend.service;

import com.workflow.general_backend.dto.CommonResult;

import java.net.URISyntaxException;

public interface TestRunService {

    CommonResult testRun(String jsonStr) throws URISyntaxException;
}
