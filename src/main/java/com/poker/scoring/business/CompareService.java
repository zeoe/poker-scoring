/* (C)2023 */
package com.poker.scoring.business;

import com.poker.scoring.model.Card;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CompareService {

  public List<Card> getStrongerHand(List<Card> hand1, List<Card> hand2) {
    int scoreHand1 = getScore(sortByValue(hand1));
    int scoreHand2 = getScore(sortByValue(hand2));

    if (scoreHand1 > scoreHand2) {
      return hand1;
    } else if (scoreHand2 > scoreHand1) {
      return hand2;
    } else {
      return Collections.emptyList();
    }
  }

  private List<Card> sortByValue(List<Card> hand) {
    return hand.stream()
        .sorted(Comparator.comparing(Card::getValue).reversed())
        .collect(Collectors.toList());
  }

  private int getScore(List<Card> sortedHand) {
    if (isStraightFlush(sortedHand)) return 1000;
    if (isFourOfAKind(sortedHand)) return 900;
    if (isFullHouse(sortedHand)) return 800;
    if (isFlush(sortedHand)) return 700;
    if (isStraight(sortedHand)) return 600;
    if (isThreeOfAKind(sortedHand)) return 500;
    if (isTwoPair(sortedHand)) return 400;
    if (isPair(sortedHand)) return 300;

    return getScoreOfHighCard(sortedHand);
  }

  private int getScoreOfHighCard(List<Card> hand) {
    return hand.get(0).getValue().getScore();
  }

  private boolean isPair(List<Card> hand) {
    return false;
  }

  private boolean isTwoPair(List<Card> hand) {
    return false;
  }

  private boolean isThreeOfAKind(List<Card> hand) {
    return false;
  }

  private boolean isStraight(List<Card> hand) {
    return false;
  }

  private boolean isFlush(List<Card> hand) {
    return false;
  }

  private boolean isFullHouse(List<Card> hand) {
    return false;
  }

  private boolean isFourOfAKind(List<Card> hand) {
    return false;
  }

  private boolean isStraightFlush(List<Card> hand) {
    return isFlush(hand) && isStraight(hand);
  }
}
