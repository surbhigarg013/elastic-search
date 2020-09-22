package com.elastic.search.poc.co;

import com.elastic.search.poc.enums.SearchOperator;

public class SearchCo {

  private String fieldName;
  private Object rangeEnd;

  public String getFieldName() {
    return fieldName;
  }

  public Object getRangeEnd() {
    return rangeEnd;
  }

  public SearchCo setRangeEnd(Object rangeEnd) {
    this.rangeEnd = rangeEnd;
    return this;
  }

  public SearchCo setFieldName(String fieldName) {
    this.fieldName = fieldName;
    return this;
  }

  public SearchOperator getOperator() {
    return operator;
  }

  public SearchCo setOperator(SearchOperator operator) {
    this.operator = operator;
    return this;
  }

  public Object getValue() {
    return value;
  }

  public SearchCo setValue(Object value) {
    this.value = value;
    return this;
  }

  private SearchOperator operator;
  private Object value;
}
