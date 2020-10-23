package com.everis.proyect.service;

import static org.mockito.Mockito.when;

import com.everis.proyect.models.Account;
import com.everis.proyect.models.Accounts;
import com.everis.proyect.models.AtmDeposit;
import com.everis.proyect.models.Card;
import com.everis.proyect.models.Cards;
import com.everis.proyect.models.Fingerprints;
import com.everis.proyect.models.Persons;
import com.everis.proyect.models.Reniec;
import io.reactivex.observers.TestObserver;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AtmDepositServiceTest {
  @InjectMocks
  private AtmDepositService atmservice;

  @Mock
  private ApiService service;

  
  @Test
  public void testbusinessif() throws Exception {
    /* FIGENGERPRINTS */
    Persons personentrada = new Persons(1L, "12345678", true, false);
    Persons personexpect = new Persons();
    personexpect.setId(personentrada.getId());
    personexpect.setDocumento(personentrada.getDocumento());
    personexpect.setFingerprint(personentrada.isFingerprint());
    personexpect.setBlacklist(personentrada.isBlacklist());
    when(atmservice.responseperson("12345678"))
          .thenReturn(personentrada);
    
    Fingerprints fingerprints = new Fingerprints("Core", true);
    Fingerprints fingerprintssalida = new Fingerprints();
    fingerprintssalida.setEntityName(fingerprints.getEntityName());
    fingerprintssalida.setSuccess(fingerprints.isSuccess());
    when(service.findFingerprintByDocument("12345678")).thenReturn(fingerprintssalida);
    
    Card card = new Card("1111222233334444", true);
    List<Card> listCard = new ArrayList<>();
    listCard.add(card);
    when(service.findCardsByDocument("12345678")).thenReturn(new Cards(listCard));
    
    Account account = new Account("1111222233334444XXX", 500.00);
    List<Account> listaccounts = new ArrayList<>();
    listaccounts.add(account);
    when(service.findAccountByCard("1111222233334444")).thenReturn(new Accounts(listaccounts));

    TestObserver<AtmDeposit> atmDeposit = atmservice.business("12345678").test();

    atmDeposit.assertSubscribed();
  }
  
  @Test
  public void testbusinesselse() throws Exception {
    /* RENIEC */
    when(atmservice.responseperson("12345678"))
          .thenReturn(new Persons(1L, "12345678", false, false));
    
    Reniec reniec =  new Reniec("Reniec", true);
    Reniec reniecsalida = new Reniec();
    reniecsalida.setEntityName(reniec.getEntityName());
    reniecsalida.setSuccess(reniec.isSuccess());
    when(service.findReniecByDocument("12345678")).thenReturn(reniecsalida);
    
    Card card = new Card("1111222233334444", true);
    Card cardsalida = new Card();
    cardsalida.setNumTarjeta(card.getNumTarjeta());
    cardsalida.setActive(card.isActive());
    List<Card> listCard = new ArrayList<>();
    listCard.add(cardsalida);
    Cards cards = new Cards(listCard);
    Cards cardssalida = new Cards();
    cardssalida.setCards(cards.getCards());
    when(service.findCardsByDocument("12345678")).thenReturn(cardssalida);
    
    Account account = new Account("1111222233334444XXX", 500.00);
    Account accountsalida = new Account();
    accountsalida.setAccountNumber(account.getAccountNumber());
    accountsalida.setAmount(account.getAmount());
    List<Account> listaccount = new ArrayList<>();
    listaccount.add(account);
    
    Accounts accounts = new Accounts(listaccount);
    Accounts accountssalida = new Accounts();
    accountssalida.setListAccount(accounts.getListAccount());
    
    when(service.findAccountByCard("1111222233334444")).thenReturn(accountssalida);
    
    TestObserver<AtmDeposit> atmDeposit = atmservice.business("12345678").test();

    atmDeposit.assertSubscribed();
  }
}
