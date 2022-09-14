package com.news;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication (exclude = DataSourceAutoConfiguration.class)
public class YourNewsApplication {

    public static void main(String[] args) {
        SpringApplication.run(YourNewsApplication.class, args);
    }

}
