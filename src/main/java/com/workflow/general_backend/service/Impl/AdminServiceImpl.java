package com.workflow.general_backend.service.Impl;

import com.workflow.general_backend.dto.AdminDto;
import com.workflow.general_backend.dto.CommonResult;
import com.workflow.general_backend.dto.CustomerDto;
import com.workflow.general_backend.entity.Admin;
import com.workflow.general_backend.entity.Customer;
import com.workflow.general_backend.mapper.AdminMapper;
import com.workflow.general_backend.mapper.CustomerMapper;
import com.workflow.general_backend.service.AdminService;
import com.workflow.general_backend.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.UUID;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    RedisTemplate redisTemplate;
    @Resource
    AdminMapper adminMapper;

    @Override
    public AdminDto alogin(Admin admin) {

        AdminDto adminDto = new AdminDto();
        Admin admin_back;
        String token = "";
        // 从数据库根据唯一的账号account搜索，返回客户类customer_back
        admin_back = adminMapper.findAdminByAccount(admin.getAccount());
        // 如果返回的客户类为空或者密码不匹配，返回空串token。
        if (admin_back == null || !Objects.equals(admin_back.getPassword(), admin.getPassword())) {
            adminDto.setToken(token);
            return adminDto;
        }

        // 获取传进来的账号account，生成token，将{account:token}存入redis
        String account = admin.getAccount();
        token = JwtUtils.getJwtToken(account);
        redisTemplate.opsForValue().set(account, token);
        // 返回给用户,将返回密码设置成空串
        admin_back.setPassword("");
        adminDto.setAdmin(admin_back);
        adminDto.setToken(token);

        return adminDto;
    }

    @Override
    public CommonResult aregister(Admin admin) {

        CommonResult commonResult = new CommonResult();
        Admin admin_back;
        admin_back = adminMapper.findAdminByAccount(admin.getAccount());
        // 判断是否已被注册
        if (admin_back != null) {
            commonResult.setStatus("Failed");
            commonResult.setMsg("user already exists");
            return commonResult;
        }
        // 生成uuid，进行注册
        String uuid = UUID.randomUUID().toString();
        admin.setAid(uuid);
        int res = adminMapper.addAdmin(admin);
        // 判断后台操作成功条数，为1即正常，为0则失败。
        if (res == 0) {
            commonResult.setStatus("Failed");
            commonResult.setMsg("backend insert failed");
            return commonResult;
        } else {
            commonResult.setStatus("OK");
            commonResult.setMsg("");
            return commonResult;
        }
    }
}
