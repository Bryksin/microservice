package com.vsware.services.classservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableReactiveFeignClients
public class ClassServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClassServiceApplication.class, args);
    }

}
