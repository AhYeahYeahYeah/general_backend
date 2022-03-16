package com.workflow.general_backend.entity;

import cn.hutool.db.Session;

import java.util.List;

public class Room {
    String sid;
    String name;
    String password;
    List<String> accountList;
    String flow;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<String> accountList) {
        this.accountList = accountList;
    }

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
    }
}
