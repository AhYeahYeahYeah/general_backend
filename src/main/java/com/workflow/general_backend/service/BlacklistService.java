package com.workflow.general_backend.service;

import com.workflow.general_backend.dto.CommonResult;
import com.workflow.general_backend.entity.Blacklist;

import java.util.List;

public interface BlacklistService {
    List<Blacklist> findAll();

    List<Blacklist> findById(String bid);

    CommonResult insert(Blacklist blacklist);

    CommonResult deleteById(String bid);

    CommonResult update(Blacklist blacklist);
}
