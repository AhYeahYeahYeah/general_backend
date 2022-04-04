package com.workflow.general_backend.service;

import com.workflow.general_backend.dto.CommonResult;
import com.workflow.general_backend.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    List<Product> findById(String pid);

    CommonResult insert(Product product);

    CommonResult deleteById(String pid);

    CommonResult update(Product product);

    Product findByName(String type, String time);
}
