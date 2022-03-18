package com.workflow.general_backend.controller;

import com.workflow.general_backend.dto.CommonResult;
import com.workflow.general_backend.entity.Admin;
import com.workflow.general_backend.service.AdminService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/v1/entity/admin")
public class AdminController {
    @Resource
    AdminService adminService;

    @GetMapping
    public List<Admin> findAll() {
        return adminService.findAll();
    }

    @GetMapping("/{aid}")
    public List<Admin> findById(@PathVariable("aid") String aid) {
        return adminService.findById(aid);
    }

    @GetMapping("/findByAccount/{account}")
    public Admin findByAccount(@PathVariable("account") String account) {
        return adminService.findByAccount(account);
    }

    @PostMapping
    public CommonResult insert(@RequestBody Admin admin) {
        return adminService.insert(admin);
    }

    @PutMapping
    public CommonResult update(@RequestBody Admin admin) {
        return adminService.update(admin);
    }

}
