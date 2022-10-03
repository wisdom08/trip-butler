package com.news.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import javax.net.ssl.SSLContext;

@Configuration //let spring know this class is a configuration class
@EnableElasticsearchRepositories(basePackages = "com.news.repository")
//to enable elasticsearch repositories from the package
@ComponentScan(basePackages = "com.news") //to let the spring know where the rest of the components are
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {
    @Value("${es.host}")
    public String elasticsearchUrl;
    @Value("${es.port}")
    public int elasticsearchPort;
    @Value("${es.username}")
    public String elasticsearchUsername;
    @Value("${es.password}")
    public String elasticsearchPassword;

    @Bean
    @Override
    public RestHighLevelClient elasticsearchClient() {  //client used to interact with elasticsearch - configuring the connection to elasticsearch
        try {
            final CredentialsProvider credentialsProvider =
                    new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY,
                    new UsernamePasswordCredentials(elasticsearchUsername, elasticsearchPassword));

            SSLContextBuilder sslBuilder = SSLContexts.custom()
                    .loadTrustMaterial(null, (x509Certificates, s) -> true);
            final SSLContext sslContext = sslBuilder.build();
            RestHighLevelClient client = new RestHighLevelClient(RestClient
                    .builder(new HttpHost(elasticsearchUrl, elasticsearchPort, "https"))
                    //port number is given as 443 since its https schema
                    .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                        @Override
                        public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                            return httpClientBuilder
                                    .setSSLContext(sslContext)
                                    .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                                    .setDefaultCredentialsProvider(credentialsProvider);
                        }
                    })
                    .setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
                        @Override
                        public RequestConfig.Builder customizeRequestConfig(
                                RequestConfig.Builder requestConfigBuilder) {
                            return requestConfigBuilder.setConnectTimeout(5000)
                                    .setSocketTimeout(120000);
                        }
                    }));
            System.out.println("elasticsearch client created");
            return client;
        } catch (Exception e) {
            System.out.println(e);
            try {
                throw new Exception("Could not create an elasticsearch client!!");
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
