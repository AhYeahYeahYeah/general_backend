package com.workflow.general_backend.controller;

import com.workflow.general_backend.dto.CommonResult;
import com.workflow.general_backend.entity.Star;
import com.workflow.general_backend.entity.Whitelist;
import com.workflow.general_backend.service.StarService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/v1/entity/star")
public class StarController {
    @Resource
    StarService starService;
    @GetMapping
    public List<Star> findAll() {
        return starService.findAll();
    }

    @GetMapping("/{cid}")
    public List<Star> findById(@PathVariable("cid") String cid) {
        return starService.findById(cid);
    }

    @PostMapping
    public CommonResult insert(@RequestBody Star star) {
        return starService.insert(star);
    }

    @PutMapping
    public CommonResult update(@RequestBody Star star) {
        return starService.update(star);
    }
}
