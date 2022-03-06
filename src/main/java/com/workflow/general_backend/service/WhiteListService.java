package com.workflow.general_backend.service;

import com.workflow.general_backend.dto.CommonResult;
import com.workflow.general_backend.entity.Whitelist;

import java.util.List;

public interface WhiteListService {
    List<Whitelist> findAll();

    List<Whitelist> findById(String wid);

    CommonResult insert(Whitelist whitelist);

    CommonResult deleteById(String wid);

    CommonResult update(Whitelist whitelist);
}
