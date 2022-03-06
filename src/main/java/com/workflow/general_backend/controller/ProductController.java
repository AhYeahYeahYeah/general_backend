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

    @GetMapping
    public List<Product> findAll() {
        return productService.findAll();
    }

    @GetMapping("/{pid}")
    public List<Product> findById(@PathVariable("pid") String pid) {
        return productService.findById(pid);
    }

    @PostMapping
    public CommonResult insert(@RequestBody Product product) {
        return productService.insert(product);
    }

    @DeleteMapping("/{pid}")
    public CommonResult deleteById(@PathVariable("pid") String pid) {
        return productService.deleteById(pid);
    }

    @PutMapping
    public CommonResult update(@RequestBody Product product) {
        return productService.update(product);
    }

}
