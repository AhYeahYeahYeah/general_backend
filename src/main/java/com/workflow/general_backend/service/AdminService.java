package com.workflow.general_backend.service;

import com.workflow.general_backend.dto.AdminDto;
import com.workflow.general_backend.dto.CommonResult;
import com.workflow.general_backend.entity.Admin;
import com.workflow.general_backend.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;


public interface AdminService {
    AdminDto alogin(Admin admin);

    CommonResult aregister(Admin admin);

    List<Admin> findAll();

    List<Admin> findById(String aid);

    Admin findByAccount(String account);

    CommonResult insert(Admin admin);

    CommonResult update(Admin admin);
}
