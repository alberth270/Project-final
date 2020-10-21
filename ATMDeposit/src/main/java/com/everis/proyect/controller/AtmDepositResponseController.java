package com.everis.proyect.controller;

import com.everis.proyect.models.AtmDeposit;
import com.everis.proyect.models.Fingerprints;
import com.everis.proyect.models.Persons;
import com.everis.proyect.models.Reniec;
import com.everis.proyect.models.ValidAccounts;
import com.everis.proyect.service.PersonService;
import io.reactivex.Single;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
  @Autowired
  PersonService personService;
  private final Logger logger = LoggerFactory.getLogger(getClass().getName());
  /***
   * 
   * @param documentNumber
   * @return
   * @throws Exception
   */
  
  @PostMapping("/deposits/")
  
  public Single<AtmDeposit> postAtmDeposit(@RequestBody String documentNumber) throws Exception {
    JSONObject document = new JSONObject(documentNumber);
    AtmDeposit atmDeposit = new AtmDeposit();
    try {
      Persons person = personService.findByDocument(document.getString("documentNumber"));
      logger.info("blacklist: " + person.isBlacklist());
      if (person.isBlacklist()) {
        logger.info("Person en lista negra, no se pudo obtener data");
        throw new Exception();
      } else {
        logger.info("Fingerprint: " + person.isFingerprint());
        if (person.isFingerprint()) {
          Fingerprints fingerprints = personService.findFingerprintByDocument(document
              .getString("documentNumber"));
          atmDeposit.setFingerprintEntityName(fingerprints.getEntityName());
        } else {
          Reniec reniec = personService.findReniecByDocument(document.getString("documentNumber"));
          atmDeposit.setFingerprintEntityName(reniec.getEntityName());
          person.setFingerprint(true);
          personService.updatePerson(person, document.getString("documentNumber"));
          logger.info("Se registro huella digital de reniec: " + documentNumber);
        }
        List<ValidAccounts> listValidAccounts = new ArrayList<ValidAccounts>();
        logger.info("documentNumber: " + document.getString("documentNumber"));
        personService.findCardsByDocument(document.getString("documentNumber")).getCards().stream()
            .filter(x -> x.isActive()).collect(Collectors.toList()).stream().forEach(card -> {
              logger.info(card.getNumTarjeta());
              personService.findAccountByCard(card.getNumTarjeta()).getListAccount().stream()
                  .forEach(accountcard -> {
                    logger.info(accountcard.getAccountNumber());
                    listValidAccounts.add(new ValidAccounts(accountcard.getAccountNumber()));
                    atmDeposit.setCustomerAmount(atmDeposit.getCustomerAmount() 
                        + accountcard.getAmount());
                  });
            }); 
        atmDeposit.setValidAccounts(listValidAccounts);
        return Single.just(atmDeposit);
      }
    } catch (NullPointerException e) {
      logger.info(e.getMessage());
      throw new Exception();
    }
  }
}
