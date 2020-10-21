package com.everis.proyect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@EnableAutoConfiguration
@SpringBootApplication
public class AtmDepositApplication {

  public static void main(String[] args) {
    SpringApplication.run(AtmDepositApplication.class, args);
  }

}
