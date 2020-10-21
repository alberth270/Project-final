package com.everis.proyect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class FingerprintApplication {

  public static void main(String[] args) {
    SpringApplication.run(FingerprintApplication.class, args);
  }

}
