package com.everis.proyect;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
  @Bean("service")
  public RestTemplate registrarPersonRest() {
    return new RestTemplate();
  }

}
