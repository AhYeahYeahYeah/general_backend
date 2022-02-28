package com.workflow.general_backend.entity;


public class Blacklist {

  private String bid;
  private String name;
  private String users;
  private String description;


  public String getBid() {
    return bid;
  }

  public void setBid(String bid) {
    this.bid = bid;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getUsers() {
    return users;
  }

  public void setUsers(String users) {
    this.users = users;
  }


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
