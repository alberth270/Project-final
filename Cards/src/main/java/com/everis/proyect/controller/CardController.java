package com.everis.proyect.controller;

import com.everis.proyect.models.Card;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import lombok.extern.log4j.Log4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequestMapping("/core")
public class CardController {
  @Value("${number-card}")
  String numberCard;
  private final Logger logger = LoggerFactory.getLogger(getClass().getName());
  /***
   * 
   * @param documentNumber
   * @return
   * @throws Exception 
   */
  
  @GetMapping("/cards")
  public Single<CardResponse> getTarjet(
        @RequestParam(value = "documentNumber") String documentNumber) throws Exception {
    logger.info("document: " + documentNumber);
    logger.info("numberCard: " + numberCard);
    try {
      return this.getResponse(numberCard);
    } catch (NullPointerException e) {
      logger.info(e.getMessage());
      throw new Exception();
    }
    
  }

  private Single<CardResponse> getResponse(String numBase) {
    CardResponse cardResponse = new CardResponse();
    List<Card> listCard = new ArrayList<Card>();
    Arrays.asList(1, 2, 3, 4, 5, 6).stream().forEach(x -> {
      String numcard = numBase + x.toString();
      boolean active = true;
      if (x > 3) {
        active = false;
      }
      Card card = new Card(numcard, active);
      listCard.add(card);
    });
    cardResponse.setCards(listCard);
    logger.info(numBase, cardResponse.getCards().stream().map(x -> x.getNumTarjeta()));
    return Single.just(cardResponse).subscribeOn(Schedulers.io());
  }
}
