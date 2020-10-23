package com.everis.proyect.controller;

import static org.mockito.Mockito.when;

import com.everis.proyect.entity.Person;
import com.everis.proyect.services.PersonService;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {

  @InjectMocks
  private PersonController personController;
  
  @Mock
  private PersonService service;
  
  @Test
  public void getPersonByDocumentTest() throws Exception {
    
    /* Mock */
    Person person = new Person();
    person.setId(1L);
    person.setDocumento("47653135");
    person.setFingerprint(true);
    person.setBlacklist(true);
    
    
    
    when(service.findbyDocumento("47653135")).thenReturn(Single.just(person));
    
    TestObserver<Person> testObserver = personController.getDocumento("47653135").test();
    /* Asserts */
    testObserver
      .assertSubscribed()
        .assertComplete();
  }
  
  @Test
  public void getPersonByDocumentExceptionTest() throws Exception {
    /* Mock */
    Person person1 = new Person(0L, null, true, false);
    when(service.findbyDocumento("47653135")).thenReturn(Single.just(person1));
    
    TestObserver<Person> testObserver = personController.getDocumento("47653135").test();
    /* Asserts */
    testObserver
      .assertSubscribed()
        .assertComplete();
  }
}
