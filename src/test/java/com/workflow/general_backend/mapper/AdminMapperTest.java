package com.workflow.general_backend.mapper;

import org.junit.jupiter.api.Test;

class AdminMapperTest {

    private AdminMapper adminMapper;

    AdminMapperTest(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    @Test
    void findAdminByAccount() {

        System.out.println(adminMapper.findAdminByAccount("12"));
    }

    @Test
    void addAdmin() {

    }

    @Test
    void testFindAdminByAccount() {
    }

    @Test
    void testAddAdmin() {
    }
}