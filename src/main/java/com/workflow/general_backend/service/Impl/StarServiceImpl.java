package com.workflow.general_backend.service.Impl;

import com.workflow.general_backend.dto.CommonResult;
import com.workflow.general_backend.entity.Star;
import com.workflow.general_backend.mapper.StarMapper;
import com.workflow.general_backend.service.StarService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
public class StarServiceImpl implements StarService {
    @Resource
    StarMapper starMapper;
    @Override
    public List<Star> findAll() {
        return starMapper.findAll();
    }

    @Override
    public List<Star> findById(String cid) {
        return starMapper.findById(cid);
    }

    @Override
    public CommonResult insert(Star star) {
        String uuid = UUID.randomUUID().toString();
        star.setSid(uuid);
        CommonResult commonResult = new CommonResult();
        try {
            int res = starMapper.insert(star);
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
    public CommonResult update(Star star) {
        CommonResult commonResult = new CommonResult();
        try {
            int res = starMapper.update(star);
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
    public CommonResult delete(String sid) {
        CommonResult commonResult = new CommonResult();
        int res = starMapper.delete(sid);
        if (res == 1) {
            commonResult.setStatus("OK");
        } else {
            commonResult.setStatus("Failed");
        }
        return commonResult;
    }
}
