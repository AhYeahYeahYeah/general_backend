package com.workflow.general_backend.entity;


public class Product {

  private String pid;
  private String productNum;
  private String validityPeriod;
  private long storage;
  private String oid;
  private String productName;
  private double annualRate;
  private double minAmount;
  private double increAmount;
  private double singlePersonLimit;
  private double singleDayLimit;
  private long riskLevel;
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


  public long getStorage() {
    return storage;
  }

  public void setStorage(long storage) {
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


  public double getAnnualRate() {
    return annualRate;
  }

  public void setAnnualRate(double annualRate) {
    this.annualRate = annualRate;
  }


  public double getMinAmount() {
    return minAmount;
  }

  public void setMinAmount(double minAmount) {
    this.minAmount = minAmount;
  }


  public double getIncreAmount() {
    return increAmount;
  }

  public void setIncreAmount(double increAmount) {
    this.increAmount = increAmount;
  }


  public double getSinglePersonLimit() {
    return singlePersonLimit;
  }

  public void setSinglePersonLimit(double singlePersonLimit) {
    this.singlePersonLimit = singlePersonLimit;
  }


  public double getSingleDayLimit() {
    return singleDayLimit;
  }

  public void setSingleDayLimit(double singleDayLimit) {
    this.singleDayLimit = singleDayLimit;
  }


  public long getRiskLevel() {
    return riskLevel;
  }

  public void setRiskLevel(long riskLevel) {
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
