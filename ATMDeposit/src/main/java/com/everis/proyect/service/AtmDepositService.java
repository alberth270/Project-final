package com.everis.proyect.service;

import com.everis.proyect.models.AtmDeposit;
import com.everis.proyect.models.Cards;
import com.everis.proyect.models.Persons;
import com.everis.proyect.models.ValidAccounts;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AtmDepositService {
  @Autowired
  ApiService service;
  private AtmDeposit atemDeposit = new AtmDeposit();
  private final Logger logger = LoggerFactory.getLogger(getClass().getName());
  /***
   * 
   * @param documentNumber
   * @return
   * @throws Exception
   */

  public Single<AtmDeposit> business(String documentNumber) throws Exception {
    Persons person = new Persons();
    person = responseperson(documentNumber);
    if (person.getId() == 0L) {
      logger.info(person.getDocumento());
      throw new Exception(person.getDocumento());
    }
    if (person.isBlacklist()) {
      logger.info("Person en lista negra, no se pudo obtener data");
      throw new Exception("Person en lista negra, no se pudo obtener data");
    } else { 
      atemDeposit.setCustomerAmount(0.0);
      responfingerprint(person);
      listcards(person);
      return Single.just(atemDeposit).subscribeOn(Schedulers.io());
    }
  }

  public Persons responseperson(String documentNumber) {
    return service.findByDocument(documentNumber);
  }

  /***
   * 
   * @param person
   */
  
  public void responfingerprint(Persons person) {
    if (person.isFingerprint()) {
      
      atemDeposit.setFingerprintEntityName(service.findFingerprintByDocument(person.getDocumento())
          .getEntityName());
    } else {
      logger.info("Se registro huella digital de reniec.");
      atemDeposit.setFingerprintEntityName(service.findReniecByDocument(person.getDocumento())
          .getEntityName());
      person.setFingerprint(true);
      service.updatePerson(person, person.getDocumento());
    }
  }

  /***
   * 
   * @param person
   */
  
  public void listcards(Persons person) {
    logger.info("Se consultan tarjetas.");
    Cards cards = new Cards(service.findCardsByDocument(person.getDocumento())
        .getCards()
        .stream() 
        .filter(x -> x.isActive())
            .collect(Collectors.toList()));
    listaccount(cards);
  }

  /***
   * 
   * @param cards
   */
  
  public void listaccount(Cards cards) {
      List<ValidAccounts> listValidAccounts = new ArrayList<>();
      cards.getCards().parallelStream()
          .forEach(card -> {
            logger.info(card.getNumTarjeta());
            service.findAccountByCard(card.getNumTarjeta()).getListAccount().stream()
                .forEach(accountcard -> {
                  logger.info(accountcard.getAccountNumber());
                  listValidAccounts.add(new ValidAccounts(accountcard.getAccountNumber()));
                  atemDeposit.setCustomerAmount(atemDeposit.getCustomerAmount() + accountcard
                      .getAmount());
                });
          });
      atemDeposit.setValidAccounts(listValidAccounts);
    }  
}
