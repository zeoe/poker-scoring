/* (C)2023 */
package com.poker.scoring.business;

import com.poker.scoring.model.Card;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CompareService {

  public List<Card> getStrongerHand(List<Card> hand1, List<Card> hand2) {
    return hand1;
  }
}
