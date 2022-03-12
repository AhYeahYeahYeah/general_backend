package com.workflow.general_backend.controller;

import com.workflow.general_backend.dto.CommonResult;
import com.workflow.general_backend.entity.Customer;
import com.workflow.general_backend.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/v1/entity/customer")
public class CustomerController {
    @Resource
    CustomerService customerService;

    @GetMapping
    public List<Customer> findAll() {
        return customerService.findAll();
    }

    @GetMapping("/dashboard")
    public List<Customer> findAllForDash() {
        return customerService.findAll();
    }

    @GetMapping("/{cid}")
    public List<Customer> findById(@PathVariable("cid") String cid) {
        return customerService.findById(cid);
    }

    @PostMapping
    public CommonResult insert(@RequestBody Customer customer) {
        return customerService.insert(customer);
    }

    @DeleteMapping("/{cid}")
    public CommonResult deleteById(@PathVariable("cid") String cid) {
        return customerService.deleteById(cid);
    }

    @PutMapping
    public CommonResult update(@RequestBody Customer customer) {
        return customerService.update(customer);
    }


}
