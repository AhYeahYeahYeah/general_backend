package com.workflow.general_backend.service;

import com.workflow.general_backend.dto.CommonResult;
import com.workflow.general_backend.entity.CustomerProfile;

import java.util.List;

public interface CustomerProfileService {
    List<CustomerProfile> findAll();

    List<CustomerProfile> findById(String cid);

    CommonResult insert(CustomerProfile customerProfile);

    CommonResult deleteById(String cid);

    CommonResult update(CustomerProfile customerProfile);
}
