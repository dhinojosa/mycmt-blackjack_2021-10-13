package com.jitterted.ebp.blackjack.domain;

import com.jitterted.ebp.blackjack.adapter.in.console.ConsoleCard;
import com.jitterted.ebp.blackjack.domain.Card;
import com.jitterted.ebp.blackjack.domain.Rank;
import com.jitterted.ebp.blackjack.domain.Suit;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.fusesource.jansi.Ansi.ansi;

class CardTest {

    private static final Suit DUMMY_SUIT = Suit.HEARTS;
    private static final Rank DUMMY_RANK = Rank.TEN;

    @Test
    public void withNumberCardHasNumericValueOfTheNumber() {
        Card card = new Card(DUMMY_SUIT, Rank.SEVEN);

        assertThat(card.rankValue())
                .isEqualTo(7);
    }

    @Test
    public void withValueOfQueenHasNumericValueOf10() {
        Card card = new Card(DUMMY_SUIT, Rank.QUEEN);

        assertThat(card.rankValue())
                .isEqualTo(10);
    }

    @Test
    public void withAceHasNumericValueOf1() {
        Card card = new Card(DUMMY_SUIT, Rank.ACE);

        assertThat(card.rankValue())
                .isEqualTo(1);
    }



}