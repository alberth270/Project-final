package com.everis.proyect.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.everis.proyect.models.Account;
import com.everis.proyect.models.Accounts;
import com.everis.proyect.models.Card;
import com.everis.proyect.models.Cards;
import com.everis.proyect.models.Fingerprints;
import com.everis.proyect.models.Persons;
import com.everis.proyect.models.Reniec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
public class ApiServiceTest {
  @InjectMocks
  private ApiService service;

  @Mock
  private RestTemplate servicerest;

  @Test
  public void testFindByDocument() throws Exception {
    when(servicerest.getForObject("http://localhost:9001/core/persons?documentNumber=1000000", Persons.class))
        .thenReturn(new Persons(1L, "47653132", true, false));
    
    Persons person2 = servicerest.getForObject("http://localhost:9001/core/persons?documentNumber=1000000",
        Persons.class);
    
    assertEquals(true, person2.isFingerprint());

    when(service.findByDocument("12345678")).thenReturn(new Persons(1L, "12345678", true, false));
    Persons person = service.findByDocument("12345678");
    
    assertEquals(true, person.isFingerprint());
  }
  
  @Test
  public void testFindReniecByDocument() throws Exception {
    when(servicerest.postForObject("http://localhost:9002/external/reniec/validate", "12345678", Reniec.class))
        .thenReturn(new Reniec("Reniec", true));
    Reniec reniecexp = servicerest.postForObject("http://localhost:9002/external/reniec/validate", "12345678",
        Reniec.class);
    
    assertEquals("Reniec", reniecexp.getEntityName());

    when(service.findReniecByDocument("12345678")).thenReturn(new Reniec("Reniec", true));
    Reniec reniec = service.findReniecByDocument("12345678");
    
    assertEquals("Reniec", reniec.getEntityName());

  }

  @Test
  public void testFindFingerprintByDocument() throws Exception {
    when(servicerest.postForObject("http://localhost:9003/core/fingerprints/validate", "12345678", Fingerprints.class))
        .thenReturn(new Fingerprints("Core", true));
    
    Fingerprints fingerprints = servicerest.postForObject("http://localhost:9003/core/fingerprints/validate",
        "12345678", Fingerprints.class);
    assertEquals("Core", fingerprints.getEntityName());
    
    when(service.findFingerprintByDocument("12345678")).thenReturn(new Fingerprints("Core", true));
    Fingerprints fingerprints2 = service.findFingerprintByDocument("12345678");
    assertEquals("Core", fingerprints2.getEntityName());
    
  }

  @Test
  public void testFindCardsByDocument() throws Exception {
    Card card = new Card("1111222233334444", true);
    List<Card> listCard = new ArrayList<>();
    listCard.add(card);
    
    when(servicerest.getForObject("http://localhost:9004/core/cards?documentNumber=" + "12345678", Cards.class))
        .thenReturn(new Cards(listCard));
    Cards cardsexp = servicerest.getForObject("http://localhost:9004/core/cards?documentNumber=" + "12345678", Cards.class);
    
    assertEquals(1, cardsexp.getCards().size());
    
    when(service.findCardsByDocument("12345678"))
        .thenReturn(new Cards(Arrays.asList(new Card("1111222233334444", true))));
    Cards cards = service.findCardsByDocument("12345678");
    assertEquals(1, cards.getCards().size());
  }

  @Test
  public void testFindAccountByCard() throws Exception {
    Account account = new Account("1111222233334444XXX", 500.00);
    List<Account> listaccounts = new ArrayList<>();
    listaccounts.add(account);
  
    when(servicerest.getForObject("http://localhost:9006/core/accounts?cardNumber=" + "1111222233334444", Accounts.class))
        .thenReturn(new Accounts(listaccounts));
    
    Accounts accountsExp =  servicerest.getForObject("http://localhost:9006/core/accounts?cardNumber=" + "1111222233334444", Accounts.class);
    assertEquals(1, accountsExp.getListAccount().size());
    
    when(service.findAccountByCard("1111222233334444"))
        .thenReturn(new Accounts(Arrays.asList(new Account("1111222233334444XXX", 500.00))));
    Accounts accounts = service.findAccountByCard("1111222233334444");
    assertEquals(500.00, accounts.getListAccount().get(0).getAmount());
  }

}
