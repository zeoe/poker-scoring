/* (C)2023 */
package com.poker.scoring.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CardValue {
  ACE(14),
  KING(13),
  QUEEN(12),
  JACK(11),
  TEN(10),
  NINE(9),
  EIGHT(8),
  SEVEN(7),
  SIX(6),
  FIVE(5),
  FOUR(4),
  THREE(3),
  TWO(2);

  private final int score;
}
