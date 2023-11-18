/* (C)2023 */
package com.poker.scoring.business;

import com.poker.scoring.model.Card;
import com.poker.scoring.model.CardSuit;
import com.poker.scoring.model.CardValue;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CompareService {

  public List<Card> getStrongerHand(List<Card> hand1, List<Card> hand2) {
    log.info("Compare hand {} with hand {}", hand1, hand2);
    int scoreHand1 = getScore(hand1);
    int scoreHand2 = getScore(hand2);

    if (scoreHand1 > scoreHand2) {
      log.info("Hand1 {} won, congratulations!", hand1);
      return hand1;
    } else if (scoreHand2 > scoreHand1) {
      log.info("Hand2 {} won, congratulations!", hand2);
      return hand2;
    } else {
      log.info("Wow, poker hands {} {} are equally strong!", hand1, hand2);
      return Collections.emptyList();
    }
  }

  private int getScore(List<Card> hand) {
    List<Card> handSorted = sortByValue(hand);
    Map<CardValue, Long> handGroupedByValue = groupByValue(hand);
    Map<CardSuit, Long> handGroupedBySuit = groupBySuit(hand);

    if (isStraightFlush(handGroupedBySuit, handSorted)) return 1000;
    if (isFourOfAKind(handGroupedByValue)) return 900;
    if (isFullHouse(handGroupedByValue)) return 800;
    if (isFlush(handGroupedBySuit)) return 700;
    if (isStraight(handSorted)) return 600;
    if (isThreeOfAKind(handGroupedByValue)) return 500;
    if (isTwoPair(handGroupedByValue)) return 400;
    if (isPair(handGroupedByValue)) return 300;

    return getScoreOfHighCard(handSorted);
  }

  private List<Card> sortByValue(List<Card> hand) {
    return hand.stream().sorted(Comparator.comparing(Card::getValue)).collect(Collectors.toList());
  }

  private Map<CardValue, Long> groupByValue(List<Card> hand) {
    return hand.stream()
        .map(Card::getValue)
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
  }

  private Map<CardSuit, Long> groupBySuit(List<Card> hand) {
    return hand.stream()
        .map(Card::getSuit)
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
  }

  private int getScoreOfHighCard(List<Card> hand) {
    return hand.get(0).getValue().getScore();
  }

  private boolean isPair(Map<CardValue, Long> hand) {
    return hand.entrySet().stream().anyMatch(m -> m.getValue() == 2L);
  }

  private boolean isTwoPair(Map<CardValue, Long> hand) {
    return hand.entrySet().stream().filter(m -> m.getValue() == 2L).count() == 2;
  }

  private boolean isThreeOfAKind(Map<CardValue, Long> hand) {
    return hand.entrySet().stream().anyMatch(m -> m.getValue() == 3L);
  }

  private boolean isStraight(List<Card> hand) {
    return false;
  }

  private boolean isFlush(Map<CardSuit, Long> hand) {
    return hand.entrySet().stream().anyMatch(m -> m.getValue() == 5L);
  }

  private boolean isFullHouse(Map<CardValue, Long> hand) {
    return hand.entrySet().stream().anyMatch(m -> m.getValue() == 2L)
        && hand.entrySet().stream().anyMatch(m -> m.getValue() == 3L);
  }

  private boolean isFourOfAKind(Map<CardValue, Long> hand) {
    return hand.entrySet().stream().anyMatch(m -> m.getValue() == 4L);
  }

  private boolean isStraightFlush(Map<CardSuit, Long> handGroupedByValue, List<Card> hand) {
    return isFlush(handGroupedByValue) && isStraight(hand);
  }
}
