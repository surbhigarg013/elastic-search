package com.elastic.search.poc.config;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

  @Value("${elastic.search.port}")
  private int elasticSearchPort;

  @Value("${elastic.search.host}")
  private String elasticSearchHost;

  @Bean
  public Client client() {
    TransportClient client = null;
    try  {
      client = new PreBuiltTransportClient(Settings.EMPTY)
          .addTransportAddress(
              new TransportAddress(InetAddress.getByName(elasticSearchHost),
                  elasticSearchPort));
      return client;
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return client;
  }

}
