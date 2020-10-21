package com.everis.proyect.repository;

import com.everis.proyect.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
  public Person findByDocumento(String documento);
}
