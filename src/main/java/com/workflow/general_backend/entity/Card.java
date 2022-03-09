package com.workflow.general_backend.entity;


public class Card {

  private String cardNum;
  private String cid;
  private float money;


  public String getCardNum() {
    return cardNum;
  }

  public void setCardNum(String cardNum) {
    this.cardNum = cardNum;
  }


  public String getCid() {
    return cid;
  }

  public void setCid(String cid) {
    this.cid = cid;
  }


  public float getMoney() {
    return money;
  }

  public void setMoney(float money) {
    this.money = money;
  }

}
