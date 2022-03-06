package com.workflow.general_backend.service.Impl;

import com.workflow.general_backend.dto.CommonResult;
import com.workflow.general_backend.entity.Whitelist;
import com.workflow.general_backend.mapper.WhiteListMapper;
import com.workflow.general_backend.service.WhiteListService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
public class WhiteListServiceImpl implements WhiteListService {
    @Resource
    WhiteListMapper whiteListMapper;

    @Override
    public List<Whitelist> findAll() {
        return whiteListMapper.findAll();
    }

    @Override
    public List<Whitelist> findById(String wid) {
        return whiteListMapper.findById(wid);
    }

    @Override
    public CommonResult insert(Whitelist whitelist) {
        CommonResult commonResult = new CommonResult();
        String uuid = UUID.randomUUID().toString();
        whitelist.setWid(uuid);
        try {
            int res = whiteListMapper.insert(whitelist);
            if (res == 1) {
                commonResult.setStatus("OK");
            } else {
                commonResult.setStatus("Failed");
            }
            return commonResult;
        }catch (DataAccessException e){
            commonResult.setStatus("Failed");
            commonResult.setMsg(e.toString());
            return commonResult;
        }
    }

    @Override
    public CommonResult deleteById(String wid) {
        CommonResult commonResult = new CommonResult();
        int res = whiteListMapper.deleteById(wid);
        if (res == 1) {
            commonResult.setStatus("OK");
        } else {
            commonResult.setStatus("Failed");
        }
        return commonResult;
    }

    @Override
    public CommonResult update(Whitelist whitelist) {
        CommonResult commonResult = new CommonResult();
        try {
            int res = whiteListMapper.update(whitelist);
            if (res == 1) {
                commonResult.setStatus("OK");
            } else {
                commonResult.setStatus("Failed");
            }
            return commonResult;
        }catch (DataAccessException e){
            commonResult.setStatus("Failed");
            commonResult.setMsg(e.toString());
            return commonResult;
        }
    }
}
