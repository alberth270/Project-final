package com.everis.proyect.models;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class AtmDeposit {
  private String fingerprintEntityName;
  private List<ValidAccounts> validAccounts;
  private double customerAmount;
}
