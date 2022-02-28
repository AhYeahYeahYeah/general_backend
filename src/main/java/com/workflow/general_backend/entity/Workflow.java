package com.workflow.general_backend.entity;


public class Workflow {

  private String fid;
  private String name;
  private String description;
  private String version;


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

}
