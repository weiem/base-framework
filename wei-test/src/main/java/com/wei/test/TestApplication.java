package com.wei.test;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@EnableConfigurationProperties
@SpringBootApplication
public class TestApplication {

    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}