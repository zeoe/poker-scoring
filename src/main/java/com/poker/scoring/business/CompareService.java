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
      return compareSamePokerHands(pokerHand1, hand1, hand2);
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

  private List<Card> compareSamePokerHands(
      PokerHand pokerHand, List<Card> hand1, List<Card> hand2) {
    switch (pokerHand) {
      case STRAIGHT_FLUSH, STRAIGHT -> {
        return compareByHighestCard(hand1, hand2);
      }
      case FOUR_OF_KIND -> {
        return compareByGroupValue(hand1, hand2, 4);
      }
      case FULL_HOUSE, THREE_OF_KIND -> {
        return compareByGroupValue(hand1, hand2, 3);
      }
      case FLUSH, HIGH_CARD -> {
        return compareByGroupValue(hand1, hand2, 1);
      }
      case TWO_PAIR, PAIR -> {
        return compareByMultipleGroupValue(hand1, hand2);
      }
      default -> {
        return Collections.emptyList();
      }
    }
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

  private boolean isPair(Map<CardValue, Long> handGroupedByValue) {
    return isValueIn(handGroupedByValue, 2L);
  }

  private boolean isTwoPair(Map<CardValue, Long> handGroupedByValue) {
    return handGroupedByValue.entrySet().stream().filter(m -> m.getValue() == 2L).count() == 2;
  }

  private boolean isThreeOfKind(Map<CardValue, Long> handGroupedByValue) {
    return isValueIn(handGroupedByValue, 3L);
  }

  private boolean isStraight(List<Card> handSorted) {
    return StreamEx.of(handSorted)
        .pairMap(
            (current, next) -> ((current.getValue().getScore() - next.getValue().getScore()) != 1))
        .findAny(card -> card)
        .isEmpty();
  }

  private boolean isFlush(Map<CardSuit, Long> handGroupedBySuit) {
    return handGroupedBySuit.entrySet().stream().anyMatch(m -> m.getValue() == 5L);
  }

  private boolean isFullHouse(Map<CardValue, Long> handGroupedByValue) {
    return isValueIn(handGroupedByValue, 2L) && isValueIn(handGroupedByValue, 3L);
  }

  private boolean isFourOfAKind(Map<CardValue, Long> handGroupedByValue) {
    return isValueIn(handGroupedByValue, 4L);
  }

  private boolean isStraightFlush(Map<CardSuit, Long> handGroupedBySuit, List<Card> handSorted) {
    return isFlush(handGroupedBySuit) && isStraight(handSorted);
  }

  private boolean isValueIn(Map<CardValue, Long> handGroupedBySuit, long value) {
    return handGroupedBySuit.entrySet().stream().anyMatch(m -> m.getValue() == value);
  }

  private List<Card> compareByHighestCard(List<Card> hand1, List<Card> hand2) {
    int score1 = sortByValue(hand1).get(0).getValue().getScore();
    int score2 = sortByValue(hand2).get(0).getValue().getScore();
    if (score1 > score2) {
      return hand1;
    } else if (score2 > score1) {
      return hand2;
    } else {
      return Collections.emptyList();
    }
  }

  private List<Card> compareByGroupValue(List<Card> hand1, List<Card> hand2, int counter) {
    List<Integer> sortedScore1 = getAllCardValuesByCount(hand1, counter);
    List<Integer> sortedScore2 = getAllCardValuesByCount(hand2, counter);

    for (int i = 0; i < sortedScore1.size(); i++) {
      if (sortedScore1.get(i) > sortedScore2.get(i)) {
        return hand1;
      } else if (sortedScore2.get(i) > sortedScore1.get(i)) {
        return hand2;
      }
    }

    return Collections.emptyList();
  }

  private List<Card> compareByMultipleGroupValue(List<Card> hand1, List<Card> hand2) {
    List<Card> winnerByGroupValue = compareByGroupValue(hand1, hand2, 2);
    if (!winnerByGroupValue.isEmpty()) {
      return winnerByGroupValue;
    }

    return compareByGroupValue(hand1, hand2, 1);
  }

  private List<Integer> getAllCardValuesByCount(List<Card> hand, int count) {
    return groupByValue(hand).entrySet().stream()
        .filter(m -> m.getValue() == count)
        .map(m -> m.getKey().getScore())
        .sorted(Collections.reverseOrder())
        .toList();
  }
}
