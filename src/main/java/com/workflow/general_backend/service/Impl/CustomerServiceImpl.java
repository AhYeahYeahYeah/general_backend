package com.workflow.general_backend.service.Impl;

import com.workflow.general_backend.dto.CommonResult;
import com.workflow.general_backend.dto.CustomerDto;
import com.workflow.general_backend.entity.Customer;
import com.workflow.general_backend.mapper.CustomerMapper;
import com.workflow.general_backend.service.CustomerService;
import com.workflow.general_backend.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    RedisTemplate redisTemplate;
    @Resource
    CustomerMapper customerMapper;
    @Override
    public CustomerDto clogin(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        Customer customer_back;
        String token="";
        // 从数据库根据唯一的账号account搜索，返回客户类customer_back
        customer_back=customerMapper.findCustomerByAccount(customer.getAccount());
        // 如果返回的客户类为空或者密码不匹配，返回空串token。
        if(customer_back==null|| !Objects.equals(customer_back.getPassword(), customer.getPassword())){
            customerDto.setToken(token);
            return customerDto;
        }

        // 获取传进来的账号account，生成token，将{account:token}存入redis
        String account=customer.getAccount();
        token=JwtUtils.getJwtToken(account);
        redisTemplate.opsForValue().set(account, token);
        // 返回给用户,将返回密码设置成空串
        customer_back.setPassword("");
        customerDto.setCustomer(customer_back);
        customerDto.setToken(token);

        return customerDto;
    }

    @Override
    public CommonResult cregister(Customer customer) {
        CommonResult commonResult=new CommonResult();
        Customer customer_back;
        customer_back=customerMapper.findCustomerByAccount(customer.getAccount());
        // 判断是否已被注册
        if(customer_back!=null){
            commonResult.setStatus("Failed");
            commonResult.setMsg("user already exists");
            return commonResult;
        }
        // 生成uuid，进行注册
        String uuid= UUID.randomUUID().toString();
        customer.setCid(uuid);
        int res=customerMapper.addCustomer(customer);
        // 判断后台操作成功条数，为1即正常，为0则失败。
        if(res==0){
            commonResult.setStatus("Failed");
            commonResult.setMsg("backend insert failed");
            return commonResult;
        }else{
            commonResult.setStatus("OK");
            commonResult.setMsg("");
            return commonResult;
        }

    }
}
