package com.workflow.general_backend.dto;

import com.alibaba.fastjson.JSONObject;
import com.workflow.general_backend.entity.Workflow;

public class WorkflowDto extends Workflow {
    private String fid;
    private String name;
    private String description;
    private String version;
    private String flow;
    private JSONObject metadataWorkflow;

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

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
    }

    public JSONObject getMetadataWorkflow() {
        return metadataWorkflow;
    }

    public void setMetadataWorkflow(JSONObject metadataWorkflow) {
        this.metadataWorkflow = metadataWorkflow;
    }
}
