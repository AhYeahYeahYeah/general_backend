package com.workflow.general_backend.entity;


import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

public class Workflow {

    private String fid;
    private String name;
    private String description;
    private String version;
    private ArrayList<JSONObject> flow;


    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public ArrayList<JSONObject> getFlow() {
        return flow;
    }

    public void setFlow(ArrayList<JSONObject> flow) {
        this.flow = flow;
    }
}
