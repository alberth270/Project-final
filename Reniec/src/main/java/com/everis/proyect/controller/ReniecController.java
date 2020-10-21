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
@RequestMapping("/external/reniec")
public class ReniecController {
  @Value("${entityName}")
  String entityName;
  @Value("${success}")
  boolean success;
  private final Logger logger = LoggerFactory.getLogger(getClass().getName());

  /***
   * @author abancesa
   * @param document
   * @return
   * @throws Exception
   */

  @PostMapping("/validate")
  public Single<ReniecResponse> getReniec(@RequestBody String document) throws Exception {
    try {
      logger.info(entityName + ", " + success);
      return Single.just(new ReniecResponse(entityName, success));
    } catch (Exception e) {
      logger.info(e.getMessage());
      throw new Exception();
    }

  }
}
