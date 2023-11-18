/* (C)2023 */
package com.poker.scoring.model;

public enum CardValue {
  TWO(2),
  THREE(3),
  FOUR(4),
  FIVE(5),
  SIX(6),
  SEVEN(7),
  EIGHT(8),
  NINE(9),
  TEN(10),
  JACK(11),
  QUEEN(11),
  KING(12),
  ACE(13);

  private final int score;

  CardValue(int score) {
    this.score = score;
  }

  public int getScore() {
    return score;
  }
}
