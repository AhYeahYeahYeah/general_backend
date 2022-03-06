package com.workflow.general_backend.service.Impl;

import com.workflow.general_backend.dto.CommonResult;
import com.workflow.general_backend.entity.Blacklist;
import com.workflow.general_backend.mapper.BlacklistMapper;
import com.workflow.general_backend.service.BlacklistService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
public class BlacklistServiceImpl implements BlacklistService {
    @Resource
    BlacklistMapper blacklistMapper;
    @Override
    public List<Blacklist> findAll() {
        return blacklistMapper.findAll();
    }

    @Override
    public List<Blacklist> findById(String bid) {
        return blacklistMapper.findById(bid);
    }

    @Override
    public CommonResult insert(Blacklist blacklist) {
        CommonResult commonResult = new CommonResult();
        String uuid = UUID.randomUUID().toString();
        blacklist.setBid(uuid);
        int res = blacklistMapper.insert(blacklist);
        if (res == 1) {
            commonResult.setStatus("OK");
        } else {
            commonResult.setStatus("Failed");
        }
        return commonResult;
    }

    @Override
    public CommonResult deleteById(String bid) {
        CommonResult commonResult = new CommonResult();
        int res = blacklistMapper.deleteById(bid);
        if (res == 1) {
            commonResult.setStatus("OK");
        } else {
            commonResult.setStatus("Failed");
        }
        return commonResult;
    }

    @Override
    public CommonResult update(Blacklist blacklist) {
        CommonResult commonResult = new CommonResult();
        int res = blacklistMapper.update(blacklist);
        if (res == 1) {
            commonResult.setStatus("OK");
        } else {
            commonResult.setStatus("Failed");
        }
        return commonResult;
    }
}
