package com.everis.proyect.service;

import com.everis.proyect.models.Persons;

public interface IServiceBase {
  public Persons findByDocument(String document);
}
