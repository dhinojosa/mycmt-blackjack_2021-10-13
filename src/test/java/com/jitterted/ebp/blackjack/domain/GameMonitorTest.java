package com.jitterted.ebp.blackjack.domain;

import com.jitterted.ebp.blackjack.domain.port.GameMonitor;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class GameMonitorTest {
    @Test
    void testPlayerStands() {
        GameMonitor gameMonitor = spy(GameMonitor.class);
        Game game = new Game(new Deck(), gameMonitor);
        game.initialDeal();
        game.playerStands();
        verify(gameMonitor).roundCompleted(any(Game.class));
    }

    @Test
    public void testPlayerHitsAndGoesBustThenResultsSentToMonitor() throws Exception {
        Deck deck = new StubDeck(
                new Card(Suit.SPADES, Rank.TEN), //player card
                new Card(Suit.HEARTS, Rank.TEN), //dealer card
                new Card(Suit.DIAMONDS, Rank.EIGHT), //player card
                new Card(Suit.DIAMONDS, Rank.FIVE), //dealer card
                new Card(Suit.CLUBS, Rank.SIX) //player card, which is a bust if drawn
        );
        GameMonitor gameMonitorSpy = spy(GameMonitor.class);
        Game game = new Game(deck, gameMonitorSpy);
        game.initialDeal();
        game.playerHits();

        verify(gameMonitorSpy).roundCompleted(any(Game.class));
    }

    @Test
    public void testPlayerHitsAndDoesNotGoBustThenResultsNotSentToMonitor() throws Exception {
        Deck deck = new StubDeck(
                new Card(Suit.SPADES, Rank.TEN), //player card
                new Card(Suit.HEARTS, Rank.TEN), //dealer card
                new Card(Suit.DIAMONDS, Rank.EIGHT), //player card
                new Card(Suit.DIAMONDS, Rank.FIVE), //dealer card
                new Card(Suit.CLUBS, Rank.THREE),   //player card
                new Card(Suit.HEARTS, Rank.THREE)   //dealer card
        );
        GameMonitor gameMonitorSpy = spy(GameMonitor.class);
        Game game = new Game(deck, gameMonitorSpy);
        game.initialDeal();
        game.playerHits();
        game.dealerTurn();

        verify(gameMonitorSpy, never()).roundCompleted(any(Game.class));
    }

    @Test
    public void testPlayerGetsBlackjackThenResultsSentToMonitor() throws Exception {
        Deck deck = new StubDeck(
                new Card(Suit.SPADES, Rank.TEN), //player card
                new Card(Suit.HEARTS, Rank.TEN), //dealer card
                new Card(Suit.DIAMONDS, Rank.ACE), //player card
                new Card(Suit.DIAMONDS, Rank.FIVE) //dealer card
        );

        GameMonitor gameMonitorSpy = spy(GameMonitor.class);
        Game game = new Game(deck, gameMonitorSpy);
        game.initialDeal();
        game.playerHits();

        verify(gameMonitorSpy).roundCompleted(any(Game.class));
    }
}
