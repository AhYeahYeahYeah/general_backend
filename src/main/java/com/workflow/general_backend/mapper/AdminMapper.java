package com.workflow.general_backend.mapper;

import com.workflow.general_backend.entity.Admin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper {
    public Admin findAdminByAccount(String account);
    public int addAdmin(Admin admin);

}
