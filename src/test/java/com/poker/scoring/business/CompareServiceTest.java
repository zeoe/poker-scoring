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
                new Card(CardSuit.CLUBS, CardValue.ACE),
                new Card(CardSuit.CLUBS, CardValue.KING),
                new Card(CardSuit.CLUBS, CardValue.QUEEN),
                new Card(CardSuit.CLUBS, CardValue.JACK),
                new Card(CardSuit.CLUBS, CardValue.TEN)),
            List.of(
                new Card(CardSuit.DIAMONDS, CardValue.ACE),
                new Card(CardSuit.SPADES, CardValue.ACE),
                new Card(CardSuit.HEARTS, CardValue.ACE),
                new Card(CardSuit.CLUBS, CardValue.ACE),
                new Card(CardSuit.HEARTS, CardValue.KING))),
        Arguments.of(
            List.of(
                new Card(CardSuit.CLUBS, CardValue.ACE),
                new Card(CardSuit.DIAMONDS, CardValue.ACE),
                new Card(CardSuit.SPADES, CardValue.ACE),
                new Card(CardSuit.HEARTS, CardValue.ACE),
                new Card(CardSuit.CLUBS, CardValue.KING)),
            List.of(
                new Card(CardSuit.DIAMONDS, CardValue.ACE),
                new Card(CardSuit.SPADES, CardValue.ACE),
                new Card(CardSuit.HEARTS, CardValue.ACE),
                new Card(CardSuit.CLUBS, CardValue.KING),
                new Card(CardSuit.HEARTS, CardValue.KING))),
        Arguments.of(
            List.of(
                new Card(CardSuit.CLUBS, CardValue.ACE),
                new Card(CardSuit.DIAMONDS, CardValue.ACE),
                new Card(CardSuit.SPADES, CardValue.ACE),
                new Card(CardSuit.HEARTS, CardValue.KING),
                new Card(CardSuit.CLUBS, CardValue.KING)),
            List.of(
                new Card(CardSuit.DIAMONDS, CardValue.TWO),
                new Card(CardSuit.DIAMONDS, CardValue.THREE),
                new Card(CardSuit.DIAMONDS, CardValue.FOUR),
                new Card(CardSuit.DIAMONDS, CardValue.QUEEN),
                new Card(CardSuit.DIAMONDS, CardValue.KING))),
        Arguments.of(
            List.of(
                new Card(CardSuit.DIAMONDS, CardValue.TWO),
                new Card(CardSuit.DIAMONDS, CardValue.THREE),
                new Card(CardSuit.DIAMONDS, CardValue.FOUR),
                new Card(CardSuit.DIAMONDS, CardValue.QUEEN),
                new Card(CardSuit.DIAMONDS, CardValue.KING)),
            List.of(
                new Card(CardSuit.DIAMONDS, CardValue.TWO),
                new Card(CardSuit.SPADES, CardValue.THREE),
                new Card(CardSuit.HEARTS, CardValue.FOUR),
                new Card(CardSuit.CLUBS, CardValue.FIVE),
                new Card(CardSuit.HEARTS, CardValue.SIX))),
        Arguments.of(
            List.of(
                new Card(CardSuit.DIAMONDS, CardValue.TWO),
                new Card(CardSuit.SPADES, CardValue.THREE),
                new Card(CardSuit.HEARTS, CardValue.FOUR),
                new Card(CardSuit.CLUBS, CardValue.FIVE),
                new Card(CardSuit.HEARTS, CardValue.SIX)),
            List.of(
                new Card(CardSuit.DIAMONDS, CardValue.TWO),
                new Card(CardSuit.HEARTS, CardValue.TWO),
                new Card(CardSuit.CLUBS, CardValue.TWO),
                new Card(CardSuit.DIAMONDS, CardValue.QUEEN),
                new Card(CardSuit.DIAMONDS, CardValue.KING))),
        Arguments.of(
            List.of(
                new Card(CardSuit.DIAMONDS, CardValue.TWO),
                new Card(CardSuit.HEARTS, CardValue.TWO),
                new Card(CardSuit.CLUBS, CardValue.TWO),
                new Card(CardSuit.DIAMONDS, CardValue.QUEEN),
                new Card(CardSuit.DIAMONDS, CardValue.KING)),
            List.of(
                new Card(CardSuit.DIAMONDS, CardValue.TEN),
                new Card(CardSuit.SPADES, CardValue.TEN),
                new Card(CardSuit.HEARTS, CardValue.JACK),
                new Card(CardSuit.CLUBS, CardValue.JACK),
                new Card(CardSuit.HEARTS, CardValue.SIX))),
        Arguments.of(
            List.of(
                new Card(CardSuit.DIAMONDS, CardValue.TEN),
                new Card(CardSuit.SPADES, CardValue.TEN),
                new Card(CardSuit.HEARTS, CardValue.JACK),
                new Card(CardSuit.CLUBS, CardValue.JACK),
                new Card(CardSuit.HEARTS, CardValue.SIX)),
            List.of(
                new Card(CardSuit.DIAMONDS, CardValue.KING),
                new Card(CardSuit.HEARTS, CardValue.KING),
                new Card(CardSuit.CLUBS, CardValue.TWO),
                new Card(CardSuit.DIAMONDS, CardValue.QUEEN),
                new Card(CardSuit.DIAMONDS, CardValue.NINE))),
        Arguments.of(
            List.of(
                new Card(CardSuit.DIAMONDS, CardValue.KING),
                new Card(CardSuit.HEARTS, CardValue.KING),
                new Card(CardSuit.CLUBS, CardValue.TWO),
                new Card(CardSuit.DIAMONDS, CardValue.QUEEN),
                new Card(CardSuit.DIAMONDS, CardValue.NINE)),
            List.of(
                new Card(CardSuit.DIAMONDS, CardValue.TEN),
                new Card(CardSuit.SPADES, CardValue.ACE),
                new Card(CardSuit.HEARTS, CardValue.SEVEN),
                new Card(CardSuit.CLUBS, CardValue.JACK),
                new Card(CardSuit.HEARTS, CardValue.SIX))),
        Arguments.of(
            List.of(
                new Card(CardSuit.DIAMONDS, CardValue.ACE),
                new Card(CardSuit.HEARTS, CardValue.QUEEN),
                new Card(CardSuit.CLUBS, CardValue.JACK),
                new Card(CardSuit.DIAMONDS, CardValue.TEN),
                new Card(CardSuit.DIAMONDS, CardValue.NINE)),
            List.of(
                new Card(CardSuit.DIAMONDS, CardValue.KING),
                new Card(CardSuit.SPADES, CardValue.TWO),
                new Card(CardSuit.HEARTS, CardValue.SEVEN),
                new Card(CardSuit.CLUBS, CardValue.JACK),
                new Card(CardSuit.HEARTS, CardValue.SIX))));
  }

  @DisplayName("Compare poker hands")
  @ParameterizedTest
  @MethodSource("providePokerHandsWithDifferentValues")
  public void comparePokerHands(List<Card> winnerHand, List<Card> loserHand) {
    List<Card> result = compareService.getStrongerHand(winnerHand, loserHand);
    assertEquals(winnerHand, result);
  }
}
