package com.thoughtmechanix.specialroutes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
@RefreshScope
@EnableEurekaClient
@EnableHystrix
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
