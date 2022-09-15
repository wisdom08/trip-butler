package com.news.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration //let spring know this class is a configuration class
@EnableElasticsearchRepositories(basePackages = "com.news.repository") //to enable elasticsearch repositories from the package
@ComponentScan(basePackages = "com.news") //to let the spring know where the rest of the components are
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {


    @Bean
    @Override
    public RestHighLevelClient elasticsearchClient() {  //client used to interact with elasticsearch - configuring the connection to elasticsearch
        final ClientConfiguration config = ClientConfiguration.builder()
                .connectedTo("34.64.119.241:9200")
                .build();
        return RestClients.create(config).rest();
    }
}
