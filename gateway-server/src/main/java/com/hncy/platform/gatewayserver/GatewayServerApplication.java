package com.hncy.platform.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableResourceServer
@EnableCircuitBreaker
@EnableEurekaClient
@EnableZuulProxy
@SpringBootApplication
public class GatewayServerApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServerApplication.class, args);
    }
}