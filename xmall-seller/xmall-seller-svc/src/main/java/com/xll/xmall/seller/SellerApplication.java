package com.xll.xmall.seller;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@EnableAutoConfiguration
public class SellerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SellerApplication.class);
    }

}
