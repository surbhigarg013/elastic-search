package com.elastic.search.poc.service;


import com.elastic.search.poc.FinancialData;
import com.elastic.search.poc.co.SearchCo;
import java.util.List;
import java.util.Map;

public interface FinancialDataService {

  String createFinancialData(FinancialData financialData);

  Map<String, Object> getFinancialData(String id);

  String updateFinancialData(String id,
      Map<String, Object> fieldAndValueMap);

  String deleteFinancialData(String id);

  List<FinancialData> searchByParams(SearchCo searchCo);
}
