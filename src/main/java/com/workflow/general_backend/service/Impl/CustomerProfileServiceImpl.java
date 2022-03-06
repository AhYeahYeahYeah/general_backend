package com.workflow.general_backend.service.Impl;

import com.workflow.general_backend.dto.CommonResult;
import com.workflow.general_backend.entity.CustomerProfile;
import com.workflow.general_backend.mapper.CustomerProfileMapper;
import com.workflow.general_backend.service.CustomerProfileService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerProfileServiceImpl implements CustomerProfileService {
    @Resource
    CustomerProfileMapper customerProfileMapper;

    @Override
    public List<CustomerProfile> findAll() {
        return customerProfileMapper.findAll();
    }

    @Override
    public List<CustomerProfile> findById(String cid) {
        return customerProfileMapper.findById(cid);
    }

    @Override
    public CommonResult insert(CustomerProfile customerProfile) {
        CommonResult commonResult = new CommonResult();
        try {
            int res = customerProfileMapper.insert(customerProfile);
            if (res == 1) {
                commonResult.setStatus("OK");
            } else {
                commonResult.setStatus("Failed");
            }
            return commonResult;
        } catch (DataAccessException e) {
            commonResult.setStatus("Failed");
            commonResult.setMsg(e.toString());
            return commonResult;
        }
    }

    @Override
    public CommonResult deleteById(String cid) {
        CommonResult commonResult = new CommonResult();
        int res = customerProfileMapper.deleteById(cid);
        if (res == 1) {
            commonResult.setStatus("OK");
        } else {
            commonResult.setStatus("Failed");
        }
        return commonResult;
    }

    @Override
    public CommonResult update(CustomerProfile customerProfile) {
        CommonResult commonResult = new CommonResult();
        try {
            int res = customerProfileMapper.update(customerProfile);
            if (res == 1) {
                commonResult.setStatus("OK");
            } else {
                commonResult.setStatus("Failed");
            }
            return commonResult;
        } catch (DataAccessException e) {
            commonResult.setStatus("Failed");
            commonResult.setMsg(e.toString());
            return commonResult;
        }
    }
}
