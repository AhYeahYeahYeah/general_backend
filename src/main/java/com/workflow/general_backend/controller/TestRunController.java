package com.workflow.general_backend.controller;

import com.workflow.general_backend.dto.CommonResult;
import com.workflow.general_backend.service.TestRunService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.net.URISyntaxException;

@CrossOrigin
@RestController
@RequestMapping("/v1/entity/testrun")
public class TestRunController {
    @Resource
    TestRunService testRunService;
    @PostMapping
    public CommonResult testRun(@RequestBody String jsonStr) throws URISyntaxException {

        return testRunService.testRun(jsonStr);
    }
}
