package com.everis.proyect.controller;

import io.reactivex.Single;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequestMapping("/core/fingerprints")
public class FingerprintController {
  @Value("${entityName}")
  String entityName;
  private final Logger logger = LoggerFactory.getLogger(getClass().getName());
  @Value("${success}")
  boolean success;
/***
 * 
 * @param document
 * @return
 * @throws Exception 
 */
  
  @PostMapping("/validate")
  public Single<FingerprintResponse> getReniec(@RequestBody String document) throws Exception {
    FingerprintResponse fingerprint = new FingerprintResponse(entityName, success);
    try {
      return Single.just(fingerprint);
    } catch (Exception e) {
      logger.info(e.getMessage());
      throw new Exception();
    }
    
  }
}
