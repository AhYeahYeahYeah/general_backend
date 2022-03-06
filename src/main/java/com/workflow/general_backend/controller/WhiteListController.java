package com.workflow.general_backend.controller;

import com.workflow.general_backend.dto.CommonResult;
import com.workflow.general_backend.entity.Whitelist;
import com.workflow.general_backend.service.WhiteListService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/v1/entity/whitelist")
public class WhiteListController {
    @Resource
    WhiteListService whiteListService;
    @GetMapping
    public List<Whitelist> findAll() {
        return whiteListService.findAll();
    }

    @GetMapping("/{wid}")
    public List<Whitelist> findById(@PathVariable("wid") String wid) {
        return whiteListService.findById(wid);
    }

    @PostMapping
    public CommonResult insert(@RequestBody Whitelist whitelist) {
        return whiteListService.insert(whitelist);
    }

    @DeleteMapping("/{wid}")
    public CommonResult deleteById(@PathVariable("wid") String wid) {
        return whiteListService.deleteById(wid);
    }

    @PutMapping
    public CommonResult update(@RequestBody Whitelist whitelist) {
        return whiteListService.update(whitelist);
    }

}
