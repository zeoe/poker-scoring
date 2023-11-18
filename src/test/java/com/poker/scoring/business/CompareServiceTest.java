/* (C)2023 */
package com.poker.scoring.business;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.poker.scoring.model.Card;
import com.poker.scoring.model.CardSuit;
import com.poker.scoring.model.CardValue;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class CompareServiceTest {

  @InjectMocks private CompareService compareService;

  private static Stream<Arguments> providePokerHandsWithDifferentValues() {
    return Stream.of(
        Arguments.of(
            List.of(
                new Card(CardSuit.CLUBS, CardValue.SIX),
                new Card(CardSuit.CLUBS, CardValue.THREE),
                new Card(CardSuit.CLUBS, CardValue.FIVE),
                new Card(CardSuit.CLUBS, CardValue.TWO),
                new Card(CardSuit.CLUBS, CardValue.FOUR)),
            List.of(
                new Card(CardSuit.CLUBS, CardValue.ACE),
                new Card(CardSuit.DIAMONDS, CardValue.ACE),
                new Card(CardSuit.SPADES, CardValue.ACE),
                new Card(CardSuit.HEARTS, CardValue.ACE),
                new Card(CardSuit.CLUBS, CardValue.KING))));
  }

  @DisplayName("Compare poker hand")
  @ParameterizedTest
  @MethodSource("providePokerHandsWithDifferentValues")
  public void comparePokerHands(List<Card> hand1, List<Card> hand2) {
    List<Card> strongerHand = compareService.getStrongerHand(hand1, hand2);
    assertEquals(hand1, strongerHand);
  }
}
