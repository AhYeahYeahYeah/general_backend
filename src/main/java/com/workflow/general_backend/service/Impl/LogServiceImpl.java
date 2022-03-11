package com.workflow.general_backend.service.Impl;

import com.workflow.general_backend.dto.CommonResult;
import com.workflow.general_backend.entity.Log;
import com.workflow.general_backend.mapper.LogMapper;
import com.workflow.general_backend.service.LogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LogServiceImpl implements LogService {
    @Resource
    LogMapper logMapper;


    @Override
    public List<Log> findAll() {
        return logMapper.findAll();
    }

    @Override
    public List<Log> findById(String lid) {
        return logMapper.findById(lid);
    }

    @Override
    public CommonResult deleteById(String lid) {
        CommonResult commonResult = new CommonResult();
        int res = logMapper.deleteById(lid);
        if (res == 1) {
            commonResult.setStatus("OK");
        } else {
            commonResult.setStatus("Failed");
        }
        return commonResult;
    }
}
