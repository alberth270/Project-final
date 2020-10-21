package com.everis.proyect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ReniecApplication {

  public static void main(String[] args) {
    SpringApplication.run(ReniecApplication.class, args);
  }
}
