package com.workflow.general_backend.entity;


public class Admin {

  private String aid;
  private String account;
  private String password;
  private String aname;
  private String nickName;
  private String avatar;
  private String permissions;


  public String getAid() {
    return aid;
  }

  public void setAid(String aid) {
    this.aid = aid;
  }


  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public String getAname() {
    return aname;
  }

  public void setAname(String aname) {
    this.aname = aname;
  }


  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }


  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }


  public String getPermissions() {
    return permissions;
  }

  public void setPermissions(String permissions) {
    this.permissions = permissions;
  }

}
