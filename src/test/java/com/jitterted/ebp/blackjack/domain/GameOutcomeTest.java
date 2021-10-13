package com.jitterted.ebp.blackjack.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class GameOutcomeTest {
    @Test
    void testPlayerBustsAndLoses() {
        Deck deck = new StubDeck(
                new Card(Suit.SPADES, Rank.TEN), //player card
                new Card(Suit.HEARTS, Rank.TEN), //dealer card
                new Card(Suit.DIAMONDS, Rank.EIGHT), //player card
                new Card(Suit.DIAMONDS, Rank.FIVE), //dealer card
                new Card(Suit.CLUBS, Rank.SIX) //player card, which is a bust if drawn
        );

        Game game = new Game(deck);
        game.initialDeal();
        game.playerHits();
        assertThat(game.determineOutcome()).isEqualTo(GameOutcome.PLAYER_BUSTS);
    }

    @Test
    void testPlayerBeatsDealer() {
        Deck deck = new StubDeck(
                new Card(Suit.SPADES, Rank.TEN), //player card
                new Card(Suit.HEARTS, Rank.TEN), //dealer card
                new Card(Suit.DIAMONDS, Rank.EIGHT), //player card
                new Card(Suit.DIAMONDS, Rank.FIVE), //dealer card
                new Card(Suit.CLUBS, Rank.THREE),   //player card
                new Card(Suit.HEARTS, Rank.THREE)   //dealer card
        );

        Game game = new Game(deck);
        game.initialDeal();
        game.playerHits();
        game.playerStands();
        game.dealerTurn();
        assertThat(game.determineOutcome()).isEqualTo(GameOutcome.PLAYER_BEATS_DEALER);
    }
}
