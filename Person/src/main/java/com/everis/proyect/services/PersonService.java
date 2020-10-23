package com.everis.proyect.services;

import com.everis.proyect.entity.Person;
import com.everis.proyect.repository.PersonRepository;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService implements BaseService<Person> {
  @Autowired
  PersonRepository personrepository;
  private final Logger logger = LoggerFactory.getLogger(getClass().getName());

  @Override
  @Transactional
  public Single<Person> findbyDocumento(String documento) {
    return Single.just(personrepository.findByDocumento(documento)).subscribeOn(Schedulers.io());
  }

  @Override
  @Transactional
  public Single<Person> update(String documento, Person entity) {
    try {
      logger.info("documento: " + documento);
      logger.info("Fingerprint: " + entity.isFingerprint());
      personrepository.findByDocumento(documento);
      return Single.just(personrepository.save(entity)).observeOn(Schedulers.io());
    } catch (NullPointerException e) {
      logger.info("No se pudo actualizar fingerprint");
      return Single.just(new Person(0L, e.getMessage(), false, false));
    }
  }
}
