package com.workflow.general_backend.dto;

import com.workflow.general_backend.entity.Admin;

public class AdminDto {
    private Admin admin;
    private String token;

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
