package com.everis.proyect.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Persons {
  private Long id;
  private String documento;
  private boolean fingerprint;
  private boolean blacklist;
}
