package com.everis.proyect.service;

import com.everis.proyect.models.Accounts;
import com.everis.proyect.models.Cards;
import com.everis.proyect.models.Fingerprints;
import com.everis.proyect.models.Persons;
import com.everis.proyect.models.Reniec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService {
  @Autowired
  private RestTemplate service;

  public Persons findByDocument(String documentNumber) {
    return service.getForObject("http://localhost:9001/core/persons?documentNumber=" + documentNumber.toString(),
        Persons.class);
  }

  public void updatePerson(Persons person, String documentNumber) {
    service.put("http://localhost:9001/core/acualiza/reniec/" + documentNumber, person, "");
  }

  public Reniec findReniecByDocument(String documentNumber) {
    return service.postForObject("http://localhost:9002/external/reniec/validate", documentNumber, Reniec.class);
  }

  public Fingerprints findFingerprintByDocument(String documentNumber) {
    return service.postForObject("http://localhost:9003/core/fingerprints/validate", documentNumber,
        Fingerprints.class);
  }

  public Cards findCardsByDocument(String documentNumber) {
    return service.getForObject("http://localhost:9004/core/cards?documentNumber=" + documentNumber, Cards.class);
  }

  public Accounts findAccountByCard(String numCard) {
    return service.getForObject("http://localhost:9006/core/accounts?cardNumber=" + numCard, Accounts.class);
  }

}
