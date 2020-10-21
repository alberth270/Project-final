package com.everis.proyect.services;

import com.everis.proyect.entity.Person;
import com.everis.proyect.repository.PersonRepository;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService implements BaseService<Person> {
  @Autowired
  PersonRepository personrepository;

  @Override
  @Transactional
  public Single<Person> findbyDocumento(String documento) {
    return Single.just(personrepository.findByDocumento(documento)).subscribeOn(Schedulers.io());
  }

  @Override
  @Transactional
  public Single<Person> update(String documento, Person entity) {
    try {
      System.out.println("documento: " + documento);
      System.out.println("Fingerprint: " + entity.isFingerprint());
      personrepository.findByDocumento(documento);
      return Single.just(personrepository.save(entity)).observeOn(Schedulers.io());
    } catch (NullPointerException e) {
      System.out.println("No se pudo actualizar fingerprint");
      return Single.just(new Person());
    }
  }
}
