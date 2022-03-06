package com.workflow.general_backend.controller;

import com.workflow.general_backend.dto.CommonResult;
import com.workflow.general_backend.entity.Product;
import com.workflow.general_backend.entity.UserGroup;
import com.workflow.general_backend.service.UserGroupService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/v1/entity/usergroup")
public class UserGroupController {
    @Resource
    UserGroupService userGroupService;

    @GetMapping
    public List<UserGroup> findAll() {
        return userGroupService.findAll();
    }

    @GetMapping("/{gid}")
    public List<UserGroup> findById(@PathVariable("gid") String gid) {
        return userGroupService.findById(gid);
    }

    @PostMapping
    public CommonResult insert(@RequestBody UserGroup userGroup) {
        return userGroupService.insert(userGroup);
    }

    @DeleteMapping("/{gid}")
    public CommonResult deleteById(@PathVariable("gid") String gid) {
        return userGroupService.deleteById(gid);
    }

    @PutMapping
    public CommonResult update(@RequestBody UserGroup userGroup) {
        return userGroupService.update(userGroup);
    }
}
