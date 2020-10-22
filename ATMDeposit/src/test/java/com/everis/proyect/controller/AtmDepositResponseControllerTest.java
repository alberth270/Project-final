package com.everis.proyect.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.everis.proyect.models.Account;
import com.everis.proyect.models.Accounts;
import com.everis.proyect.models.AtmDeposit;
import com.everis.proyect.models.Card;
import com.everis.proyect.models.Cards;
import com.everis.proyect.models.Fingerprints;
import com.everis.proyect.models.Persons;
import com.everis.proyect.models.Reniec;
import com.everis.proyect.models.ResponseFingerprint;
import com.everis.proyect.models.ValidAccounts;
import com.everis.proyect.service.ApiService;
import com.everis.proyect.service.AtmDepositService;

import ch.qos.logback.core.status.StatusListenerAsList;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

import org.codehaus.jettison.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class AtmDepositResponseControllerTest {
  
  @InjectMocks
  AtmDepositResponseController controller;
  
  @Mock
  AtmDepositService atmDepositService;
  
  /**
   * @throws Exception *
   * 
   */

  
  @Test
  public void testPostAtmDeposit() throws Exception {
    JSONObject document = new JSONObject("12345678");
    try {
      return atmDepositService.business(document.getString("documentNumber"));
    } catch (Exception e) {
      logger.info(e.getMessage());
      return Single.just(new AtmDeposit(e.getMessage(), null, 0.00));
    }
    
  }
}
