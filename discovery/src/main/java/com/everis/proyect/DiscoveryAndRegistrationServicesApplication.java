package com.everis.proyect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class DiscoveryAndRegistrationServicesApplication {

  public static void main(String[] args) {
    SpringApplication.run(DiscoveryAndRegistrationServicesApplication.class, args);
  }

}
