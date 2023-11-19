/* (C)2023 */
package com.poker.scoring.business;

import com.poker.scoring.model.Card;
import com.poker.scoring.model.CardSuit;
import com.poker.scoring.model.CardValue;
import com.poker.scoring.model.PokerHand;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import one.util.streamex.StreamEx;
import org.springframework.stereotype.Service;

@Service
public class CompareService {

  public List<Card> getStrongerHand(List<Card> hand1, List<Card> hand2) {
    PokerHand pokerHand1 = getPokerHand(hand1);
    PokerHand pokerHand2 = getPokerHand(hand2);

    if (pokerHand1.getScore() > pokerHand2.getScore()) {
      return hand1;
    } else if (pokerHand2.getScore() > pokerHand1.getScore()) {
      return hand2;
    } else {
      return Collections.emptyList();
    }
  }

  private PokerHand getPokerHand(List<Card> hand) {
    List<Card> handSorted = sortByValue(hand);
    Map<CardValue, Long> handGroupedByValue = groupByValue(hand);
    Map<CardSuit, Long> handGroupedBySuit = groupBySuit(hand);

    if (isStraightFlush(handGroupedBySuit, handSorted)) return PokerHand.STRAIGHT_FLUSH;
    if (isFourOfAKind(handGroupedByValue)) return PokerHand.FOUR_OF_KIND;
    if (isFullHouse(handGroupedByValue)) return PokerHand.FULL_HOUSE;
    if (isFlush(handGroupedBySuit)) return PokerHand.FLUSH;
    if (isStraight(handSorted)) return PokerHand.STRAIGHT;
    if (isThreeOfKind(handGroupedByValue)) return PokerHand.THREE_OF_KIND;
    if (isTwoPair(handGroupedByValue)) return PokerHand.TWO_PAIR;
    if (isPair(handGroupedByValue)) return PokerHand.PAIR;

    return PokerHand.HIGH_CARD;
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

  private boolean isPair(Map<CardValue, Long> hand) {
    return isValueIn(hand, 2L);
  }

  private boolean isTwoPair(Map<CardValue, Long> hand) {
    return hand.entrySet().stream().filter(m -> m.getValue() == 2L).count() == 2;
  }

  private boolean isThreeOfKind(Map<CardValue, Long> hand) {
    return isValueIn(hand, 3L);
  }

  private boolean isStraight(List<Card> hand) {
    return StreamEx.of(hand)
        .pairMap(
            (current, next) -> ((current.getValue().getScore() - next.getValue().getScore()) != 1))
        .findAny(card -> card)
        .isEmpty();
  }

  private boolean isFlush(Map<CardSuit, Long> hand) {
    return hand.entrySet().stream().anyMatch(m -> m.getValue() == 5L);
  }

  private boolean isFullHouse(Map<CardValue, Long> hand) {
    return isValueIn(hand, 2L) && isValueIn(hand, 3L);
  }

  private boolean isFourOfAKind(Map<CardValue, Long> hand) {
    return isValueIn(hand, 4L);
  }

  private boolean isStraightFlush(Map<CardSuit, Long> handGroupedByValue, List<Card> hand) {
    return isFlush(handGroupedByValue) && isStraight(hand);
  }

  private boolean isValueIn(Map<CardValue, Long> hand, long value) {
    return hand.entrySet().stream().anyMatch(m -> m.getValue() == value);
  }
}
