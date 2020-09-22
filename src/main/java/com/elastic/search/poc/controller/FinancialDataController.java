package com.elastic.search.poc.controller;

import com.elastic.search.poc.FinancialData;
import com.elastic.search.poc.co.SearchCo;
import com.elastic.search.poc.service.FinancialDataService;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FinancialDataController {

  @Autowired
  private FinancialDataService financialDataService;

  @PostMapping("/financial-data")
  public String saveFinancialData(@RequestBody FinancialData financialData) {
    return financialDataService.createFinancialData(financialData);
  }

  @GetMapping("/financial-data/{id}")
  public Map<String, Object> view(@PathVariable final String id) {
   return financialDataService.getFinancialData(id);
  }

  @PutMapping("/financial-data/{id}")
  public String update(@PathVariable final String id,
      @RequestBody Map<String, Object> fieldAndValueMap) throws IOException {
    return financialDataService.updateFinancialData(id, fieldAndValueMap);
  }

  @DeleteMapping("/financial-data/{id}")
  public String delete(@PathVariable final String id) {
    return financialDataService.deleteFinancialData(id);
  }

  @PostMapping("/financial-data/by-params")
  public List<FinancialData> searchByParams(@RequestBody SearchCo searchCo) {
    return financialDataService.searchByParams(searchCo);
  }
}
