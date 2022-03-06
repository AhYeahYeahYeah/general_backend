package com.workflow.general_backend.mapper;

import com.workflow.general_backend.entity.Admin;
import com.workflow.general_backend.entity.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {
    Admin findAdminByAccount(String account);
    
    List<Admin> findAll();

    List<Admin> findById(String aid);

    int insert(Admin admin);

    int update(Admin admin);
}
