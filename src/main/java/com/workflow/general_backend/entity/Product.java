package com.workflow.general_backend.entity;


public class Product {

  private String pid;
  private String productNum;
  private String validityPeriod;
  private int storage;
  private String oid;
  private String productName;
  private float annualRate;
  private float minAmount;
  private float increAmount;
  private float singlePersonLimit;
  private float singleDayLimit;
  private int riskLevel;
  private String settlementMethod;
  private String fid;


  public String getPid() {
    return pid;
  }

  public void setPid(String pid) {
    this.pid = pid;
  }


  public String getProductNum() {
    return productNum;
  }

  public void setProductNum(String productNum) {
    this.productNum = productNum;
  }


  public String getValidityPeriod() {
    return validityPeriod;
  }

  public void setValidityPeriod(String validityPeriod) {
    this.validityPeriod = validityPeriod;
  }


  public int getStorage() {
    return storage;
  }

  public void setStorage(int storage) {
    this.storage = storage;
  }


  public String getOid() {
    return oid;
  }

  public void setOid(String oid) {
    this.oid = oid;
  }


  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }


  public float getAnnualRate() {
    return annualRate;
  }

  public void setAnnualRate(float annualRate) {
    this.annualRate = annualRate;
  }


  public float getMinAmount() {
    return minAmount;
  }

  public void setMinAmount(float minAmount) {
    this.minAmount = minAmount;
  }


  public float getIncreAmount() {
    return increAmount;
  }

  public void setIncreAmount(float increAmount) {
    this.increAmount = increAmount;
  }


  public float getSinglePersonLimit() {
    return singlePersonLimit;
  }

  public void setSinglePersonLimit(float singlePersonLimit) {
    this.singlePersonLimit = singlePersonLimit;
  }


  public float getSingleDayLimit() {
    return singleDayLimit;
  }

  public void setSingleDayLimit(float singleDayLimit) {
    this.singleDayLimit = singleDayLimit;
  }


  public int getRiskLevel() {
    return riskLevel;
  }

  public void setRiskLevel(int riskLevel) {
    this.riskLevel = riskLevel;
  }


  public String getSettlementMethod() {
    return settlementMethod;
  }

  public void setSettlementMethod(String settlementMethod) {
    this.settlementMethod = settlementMethod;
  }


  public String getFid() {
    return fid;
  }

  public void setFid(String fid) {
    this.fid = fid;
  }

}
