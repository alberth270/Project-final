package com.everis.proyect.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ResourceBundle.Control;

import com.everis.proyect.models.AtmDeposit;
import com.everis.proyect.models.ValidAccounts;
import com.everis.proyect.service.AtmDepositService;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class AtmDepositResponseControllerTest {
  @InjectMocks
  private AtmDepositResponseController controller;
  
  @Mock
  private AtmDepositService service;
  
  @Test
  public void testPostAtmDeposit() throws Exception {
    when(service.business("12345678")).thenReturn(Single.just(new AtmDeposit("Reniec", 
        null, 10.00)));
    TestObserver<AtmDeposit> testobs = service.business("12345678").test();
    
    testobs
    .assertSubscribed()
    .assertComplete()
    .assertValue(cards -> cards.getFingerprintEntityName().equals("Reniec"))
    .assertValueCount(1)
        .assertNoErrors();
  }

}
