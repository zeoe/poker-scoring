/* (C)2023 */
package com.poker.scoring.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Card {
  private CardSuit suit;
  private CardValue value;
}
