package com.workflow.general_backend.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@SpringBootTest
class AdminMapperTest {

    @Resource
    private AdminMapper adminMapper;
    @Test
    @Transactional
    void findAdminByAccount() {
        
    }
}