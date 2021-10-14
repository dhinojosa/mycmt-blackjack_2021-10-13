package com.jitterted.ebp.blackjack.adapter.in.console;

import com.jitterted.ebp.blackjack.domain.Card;
import com.jitterted.ebp.blackjack.domain.Rank;
import com.jitterted.ebp.blackjack.domain.Suit;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.fusesource.jansi.Ansi.ansi;

public class CardDisplayTest {

    private static final Suit DUMMY_SUIT = Suit.HEARTS;
    private static final Rank DUMMY_RANK = Rank.TEN;

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
    @Test
    public void suitOfHeartsOrDiamondsIsDisplayedInRed() {
        // given a card with Hearts or Diamonds
        Card heartsCard = new Card(Suit.HEARTS, DUMMY_RANK);
        Card diamondsCard = new Card(Suit.DIAMONDS, DUMMY_RANK);

        // when we ask for its display representation
        String ansiRedString = ansi().fgRed().toString();

        // then we expect a red color ansi sequence
        assertThat(ConsoleCard.display(heartsCard))
                .contains(ansiRedString);
        assertThat(ConsoleCard.display(diamondsCard))
                .contains(ansiRedString);
    }
}
