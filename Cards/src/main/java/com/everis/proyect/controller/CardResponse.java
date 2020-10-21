package com.everis.proyect.controller;

import com.everis.proyect.models.Card;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CardResponse {
  private List<Card> cards;
}
