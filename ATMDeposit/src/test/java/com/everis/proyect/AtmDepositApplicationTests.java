package com.everis.proyect;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AtmDepositApplicationTests {

  @Test
  void contextLoads() {
    int num1 = 2;
    int num2 = 2;
    
    assertEquals(4, num1 + num2);
  }

}
