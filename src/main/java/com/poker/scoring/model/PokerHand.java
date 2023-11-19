/* (C)2023 */
package com.poker.scoring.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PokerHand {
  STRAIGHT_FLUSH(9),
  FOUR_OF_KIND(8),
  FULL_HOUSE(7),
  FLUSH(6),
  STRAIGHT(5),
  THREE_OF_KIND(4),
  TWO_PAIR(3),
  PAIR(2),
  HIGH_CARD(1);

  private final int score;
}
