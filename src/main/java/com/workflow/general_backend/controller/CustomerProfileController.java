package com.workflow.general_backend.controller;

import com.workflow.general_backend.dto.CommonResult;
import com.workflow.general_backend.entity.CustomerProfile;
import com.workflow.general_backend.service.CustomerProfileService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/v1/entity/customerprofile")
public class CustomerProfileController {
    @Resource
    CustomerProfileService customerProfileService;

    @GetMapping
    public List<CustomerProfile> findAll() {
        return customerProfileService.findAll();
    }

    @GetMapping("/{cid}")
    public List<CustomerProfile> findById(@PathVariable("cid") String cid) {
        return customerProfileService.findById(cid);
    }

    @PostMapping
    public CommonResult insert(@RequestBody CustomerProfile customerProfile) {
        return customerProfileService.insert(customerProfile);
    }

    @DeleteMapping("/{cid}")
    public CommonResult deleteById(@PathVariable("cid") String cid) {
        return customerProfileService.deleteById(cid);
    }

    @PutMapping
    public CommonResult update(@RequestBody CustomerProfile customerProfile) {
        return customerProfileService.update(customerProfile);
    }

}
