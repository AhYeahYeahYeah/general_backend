package com.workflow.general_backend.controller;

import com.workflow.general_backend.dto.CommonResult;
import com.workflow.general_backend.entity.Blacklist;
import com.workflow.general_backend.entity.Product;
import com.workflow.general_backend.service.BlacklistService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/v1/entity/blacklist")
public class BlacklistController {
    @Resource
    BlacklistService blacklistService;
    @GetMapping
    public List<Blacklist> findAll() {
        return blacklistService.findAll();
    }

    @GetMapping("/{bid}")
    public List<Blacklist> findById(@PathVariable("bid") String bid) {
        return blacklistService.findById(bid);
    }

    @PostMapping
    public CommonResult insert(@RequestBody Blacklist blacklist) {
        return blacklistService.insert(blacklist);
    }

    @DeleteMapping("/{bid}")
    public CommonResult deleteById(@PathVariable("bid") String bid) {
        return blacklistService.deleteById(bid);
    }

    @PutMapping
    public CommonResult update(@RequestBody Blacklist blacklist) {
        return blacklistService.update(blacklist);
    }

}
