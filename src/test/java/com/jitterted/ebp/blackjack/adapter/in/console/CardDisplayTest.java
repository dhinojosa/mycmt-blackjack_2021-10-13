package com.jitterted.ebp.blackjack.adapter.in.console;

import com.jitterted.ebp.blackjack.adapter.in.console.ConsoleCard;
import com.jitterted.ebp.blackjack.domain.Card;
import com.jitterted.ebp.blackjack.domain.Rank;
import com.jitterted.ebp.blackjack.domain.Suit;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CardDisplayTest {
    @Test
    void testDisplayTenAsString() {
        Card card = new Card(Suit.HEARTS, Rank.TEN);
        String result = ConsoleCard.display(card);
        assertThat(result).isEqualTo("[31m┌─────────┐[1B[11D│10       │[1B[11D│         │[1B[11D│    ♥    │[1B[11D│         │[1B[11D│       10│[1B[11D└─────────┘");
    }

    @Test
    void testDisplayNonTenAsString() {
        Card card = new Card(Suit.HEARTS, Rank.EIGHT);
        String result = ConsoleCard.display(card);
        assertThat(result).isEqualTo("[31m┌─────────┐[1B[11D│8        │[1B[11D│         │[1B[11D│    ♥    │[1B[11D│         │[1B[11D│        8│[1B[11D└─────────┘");
    }
}
