package com.elastic.search.poc.service.impl;

import static org.apache.logging.log4j.util.Strings.EMPTY;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

import com.elastic.search.poc.FinancialData;
import com.elastic.search.poc.co.SearchCo;
import com.elastic.search.poc.service.FinancialDataService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.testng.collections.CollectionUtils;

@Service
public class FinancialDataServiceImpl implements FinancialDataService {

  public static final String COMPANIES = "companies";
  public static final String FINANCIAL_DATA = "financial-data";
  @Autowired
  private Client client;

  @Override
  public String createFinancialData(FinancialData financialData) {
    ObjectMapper mapper = new ObjectMapper();
    IndexResponse indexResponse = client.prepareIndex(COMPANIES, FINANCIAL_DATA)
        .setSource(mapper.convertValue(financialData, Map.class))
        .get();
    return String.join(EMPTY, "Created with id :", indexResponse.getId());
  }

  @Override
  public Map<String, Object> getFinancialData(String id) {
    GetResponse getResponse = client.prepareGet(COMPANIES, FINANCIAL_DATA, id).get();
    return getResponse.getSourceAsMap();
  }

  @Override
  public String updateFinancialData(String id,
      Map<String, Object> fieldAndValueMap) {
    UpdateRequest updateRequest = new UpdateRequest();
    updateRequest.index(COMPANIES)
        .type(FINANCIAL_DATA)
        .id(id)
        .doc(fieldAndValueMap);
    try {
      UpdateResponse updateResponse = client.update(updateRequest).get();
      return updateResponse.status().toString();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
      Thread.currentThread().interrupt();
    }
    return "Exception";
  }

  @Override
  public String deleteFinancialData(String id) {
    DeleteResponse deleteResponse = client.prepareDelete(COMPANIES, FINANCIAL_DATA, id).get();
    return deleteResponse.getResult().toString();
  }

  @Override
  public List<FinancialData> searchByParams(SearchCo searchCo) {
    Map<String, Object> map = null;
    List<FinancialData> financialDataList = new ArrayList<>();
    SearchResponse response = client.prepareSearch(COMPANIES)
        .setTypes(FINANCIAL_DATA)
        .setSearchType(SearchType.QUERY_AND_FETCH)
        .setQuery(getQuery(searchCo)).get();
    List<SearchHit> searchHits = Arrays.asList(response.getHits().getHits());
    if (CollectionUtils.hasElements(searchHits)) {
      ObjectMapper mapper = new ObjectMapper();
      searchHits.forEach(hit -> {
        financialDataList.add(mapper.convertValue(hit.getSourceAsMap(), FinancialData.class));
      });
    }
    return financialDataList;
  }

  private QueryBuilder getQuery(SearchCo searchCo) {
    switch (searchCo.getOperator()) {
      case EQUALS:
        return QueryBuilders.matchQuery(searchCo.getFieldName(), searchCo.getValue());
      case NOT_EQUALS:
        BoolQueryBuilder boolQuey = QueryBuilders.boolQuery()
            .mustNot(QueryBuilders.matchQuery(searchCo.getFieldName(), searchCo.getValue())
                .setLenient(false)
                .fuzzyTranspositions(false).operator(Operator.AND)
            );
        return boolQuey;
      case CONTAINS:
        BoolQueryBuilder containsQuery = QueryBuilders.boolQuery()
            .must(QueryBuilders.matchQuery(searchCo.getFieldName(), searchCo.getValue())
                .operator(Operator.AND)
            );
        return containsQuery;
      case RANGE:
       return QueryBuilders.rangeQuery(searchCo.getFieldName()).gte(searchCo.getValue())
            .lte(searchCo.getRangeEnd());
    }
    return null;
  }
}
