package com.workflow.general_backend.entity;


public class Customer {

  private String cid;
  private String account;
  private String password;
  private String cname;
  private String nickName;
  private String avatar;


  public String getCid() {
    return cid;
  }

  public void setCid(String cid) {
    this.cid = cid;
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


  public String getCname() {
    return cname;
  }

  public void setCname(String cname) {
    this.cname = cname;
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

}
