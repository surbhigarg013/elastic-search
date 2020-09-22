package com.elastic.search.poc;

import java.io.Serializable;
import java.math.BigDecimal;

public class FinancialData implements Serializable {

  private String company;
  private String state;

  public String getCompany() {
    return company;
  }

  public FinancialData setCompany(String company) {
    this.company = company;
    return this;
  }

  public String getState() {
    return state;
  }

  public FinancialData setState(String state) {
    this.state = state;
    return this;
  }

  public Double getTurnOver() {
    return turnOver;
  }

  public FinancialData setTurnOver(Double turnOver) {
    this.turnOver = turnOver;
    return this;
  }

  private Double turnOver;



}
