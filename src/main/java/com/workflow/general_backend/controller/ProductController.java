package com.workflow.general_backend.controller;

import com.workflow.general_backend.dto.CommonResult;
import com.workflow.general_backend.entity.Product;
import com.workflow.general_backend.service.ProductService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/v1/entity/product")
public class ProductController {
    @Resource
    ProductService productService;

    @GetMapping("/get/")
    public List<Product> findAll() {
        return productService.findAll();
    }

    @GetMapping("/get/{pid}")
    public List<Product> findById(@PathVariable("pid") String pid) {
        return productService.findById(pid);
    }

    @PostMapping("/post")
    public CommonResult insert(Product product) {
        return productService.insert(product);
    }

    @DeleteMapping("/delete/{pid}")
    public CommonResult deleteById(@PathVariable("pid") String pid) {
        return productService.deleteById(pid);
    }

    @PutMapping("/put")
    public CommonResult update(Product product) {
        return productService.update(product);
    }

}
