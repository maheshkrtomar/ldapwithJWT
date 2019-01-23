package com.mkt.corevalue.corevalueservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.infogain.corevalue.corevalueservice")
@EnableDiscoveryClient
public class CorevalueserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CorevalueserviceApplication.class, args);
	}
}
