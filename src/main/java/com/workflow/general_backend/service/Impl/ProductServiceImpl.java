package com.workflow.general_backend.service.Impl;

import com.workflow.general_backend.dto.CommonResult;
import com.workflow.general_backend.entity.Product;
import com.workflow.general_backend.mapper.ProductMapper;
import com.workflow.general_backend.service.ProductService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    @Resource
    ProductMapper productMapper;

    @Override
    public List<Product> findAll() {
        return productMapper.findAll();
    }

    @Override
    public List<Product> findById(String pid) {
        return productMapper.findById(pid);
    }

    @Override
    public CommonResult insert(Product product){
        CommonResult commonResult = new CommonResult();
        String uuid = UUID.randomUUID().toString();
        product.setPid(uuid);
        try {
            int res = productMapper.insert(product);
            if (res == 1) {
                commonResult.setStatus("OK");
            } else {
                commonResult.setStatus("Failed");
            }
            return commonResult;
        }catch (DataAccessException e){
            commonResult.setStatus("Failed");
            commonResult.setMsg(e.toString());
            return commonResult;
        }
    }

    @Override
    public CommonResult deleteById(String pid) {
        CommonResult commonResult = new CommonResult();
        int res = productMapper.deleteById(pid);
        if (res == 1) {
            commonResult.setStatus("OK");
        } else {
            commonResult.setStatus("Failed");
        }
        return commonResult;
    }

    @Override
    public CommonResult update(Product product) {
        CommonResult commonResult = new CommonResult();
        try {
            int res = productMapper.update(product);
            if (res == 1) {
                commonResult.setStatus("OK");
            } else {
                commonResult.setStatus("Failed");
            }
            return commonResult;
        }catch (DataAccessException e){
            commonResult.setStatus("Failed");
            commonResult.setMsg(e.toString());
            return commonResult;
        }

    }
}
