package com.workflow.general_backend.service.Impl;

import com.workflow.general_backend.dto.CommonResult;
import com.workflow.general_backend.entity.UserGroup;
import com.workflow.general_backend.mapper.UserGroupMapper;
import com.workflow.general_backend.service.UserGroupService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
public class UserGroupServiceImpl implements UserGroupService {
    @Resource
    UserGroupMapper userGroupMapper;
    @Override
    public List<UserGroup> findAll() {
        return userGroupMapper.findAll();
    }

    @Override
    public List<UserGroup> findById(String gid) {
        return userGroupMapper.findById(gid);
    }

    @Override
    public CommonResult insert(UserGroup userGroup) {
        CommonResult commonResult = new CommonResult();
        String uuid = UUID.randomUUID().toString();
        userGroup.setGid(uuid);
        try {
            int res = userGroupMapper.insert(userGroup);
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
    public CommonResult deleteById(String gid) {
        CommonResult commonResult = new CommonResult();
        int res = userGroupMapper.deleteById(gid);
        if (res == 1) {
            commonResult.setStatus("OK");
        } else {
            commonResult.setStatus("Failed");
        }
        return commonResult;
    }

    @Override
    public CommonResult update(UserGroup userGroup) {
        CommonResult commonResult = new CommonResult();
        try {
            int res = userGroupMapper.update(userGroup);
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
