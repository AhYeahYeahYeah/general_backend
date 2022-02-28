package com.workflow.general_backend.service;

import com.workflow.general_backend.dto.AdminDto;
import com.workflow.general_backend.dto.CommonResult;
import com.workflow.general_backend.entity.Admin;
import org.springframework.stereotype.Service;


public interface AdminService {
    AdminDto alogin(Admin admin);

    CommonResult aregister(Admin admin);
}
