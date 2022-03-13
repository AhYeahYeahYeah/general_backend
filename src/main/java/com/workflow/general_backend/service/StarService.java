package com.workflow.general_backend.service;

import com.workflow.general_backend.dto.CommonResult;
import com.workflow.general_backend.entity.Star;
import com.workflow.general_backend.entity.UserGroup;

import java.util.List;

public interface StarService {
    List<Star> findAll();

    List<Star> findById(String cid);

    CommonResult insert(Star star);

    CommonResult update(Star star);

    CommonResult delete(Star star);
}
