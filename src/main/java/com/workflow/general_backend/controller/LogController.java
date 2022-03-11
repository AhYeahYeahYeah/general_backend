package com.workflow.general_backend.controller;


import com.workflow.general_backend.dto.CommonResult;
import com.workflow.general_backend.entity.Log;
import com.workflow.general_backend.service.LogService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/v1/entity/log")
public class LogController {
    @Resource
    LogService logService;

    @GetMapping
    public List<Log> findAll() {
        return logService.findAll();
    }

    @GetMapping("/{lid}")
    public List<Log> findById(@PathVariable("lid") String lid) {
        return logService.findById(lid);
    }

    @DeleteMapping("/{lid}")
    public CommonResult deleteById(@PathVariable("lid") String lid) {
        return logService.deleteById(lid);
    }


}
