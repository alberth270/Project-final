package com.everis.proyect.controller;

import com.everis.proyect.models.AtmDeposit;
import com.everis.proyect.service.AtmDepositService;
import io.reactivex.Single;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequestMapping("/atm")
public class AtmDepositResponseController {
  private final Logger logger = LoggerFactory.getLogger(getClass().getName());
  @Autowired
  AtmDepositService atmDepositService;
  

  /***
   * 
   * @param documentNumber
   * @return
   * @throws Exception
   */

  @PostMapping("/deposits/")
  public Single<AtmDeposit> postAtmDeposit(@RequestBody String documentNumber) throws Exception {
    JSONObject document = new JSONObject(documentNumber);
    logger.info("Inicio proceso AtmDeposit");
    try {
      return atmDepositService.business(document.getString("documentNumber"));
    } catch (Exception e) {
      logger.info(e.getMessage());
      return Single.just(new AtmDeposit(e.getMessage(), null, 0.00));
    }
  }
}
