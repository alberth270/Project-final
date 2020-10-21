package com.everis.proyect.services;

import com.everis.proyect.entity.Person;
import io.reactivex.Single;

public interface BaseService<E> {
  public Single<E> findbyDocumento(String documento);

  public Single<Person> update(String documento, Person entity);
}
