package com.workflow.general_backend.service;

import com.workflow.general_backend.dto.CommonResult;
import com.workflow.general_backend.entity.UserGroup;

import java.util.List;

public interface UserGroupService {
    List<UserGroup> findAll();

    List<UserGroup> findById(String gid);

    CommonResult insert(UserGroup userGroup);

    CommonResult deleteById(String gid);

    CommonResult update(UserGroup userGroup);
}
