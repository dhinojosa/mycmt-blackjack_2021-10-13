package com.jitterted.ebp.blackjack.adapter.in.web;

import com.jitterted.ebp.blackjack.domain.Card;
import com.jitterted.ebp.blackjack.domain.Deck;
import com.jitterted.ebp.blackjack.domain.Game;
import com.jitterted.ebp.blackjack.domain.GameOutcome;
import com.jitterted.ebp.blackjack.domain.Rank;
import com.jitterted.ebp.blackjack.domain.StubDeck;
import com.jitterted.ebp.blackjack.domain.Suit;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

//Unit
public class BlackjackControllerTest {


    @Test
    void testThatStartOfGameIsInitialized() {
        //Domain
        Game game = new Game(new Deck());
        BlackjackController blackjackController = new BlackjackController(() -> game);
        blackjackController.startGame();

        List<Card> result = game.dealerHand().cards();
        assertThat(result).hasSize(2);
    }

    @Test
    public void gameViewPopulatesViewModelWithAllCards() {
        Deck stubDeck = new StubDeck(new Card(Suit.DIAMONDS, Rank.TEN),
                                             new Card(Suit.HEARTS, Rank.TWO),
                                             new Card(Suit.DIAMONDS, Rank.KING),
                                             new Card(Suit.CLUBS, Rank.THREE));
        Game game = new Game(stubDeck);
        BlackjackController blackjackController = new BlackjackController(() -> game);
        blackjackController.startGame();

        Model model = new ConcurrentModel();
        blackjackController.viewGame(model);

        GameView gameView = (GameView) model.getAttribute("gameView");

        assertThat(gameView.getDealerCards())
                .containsExactly("2♥", "3♣");

        assertThat(gameView.getPlayerCards())
                .containsExactly("10♦", "K♦");
    }

    @Test
    void testHitCommandAndNotBusted() {
        Deck stubDeck = new StubDeck(new Card(Suit.DIAMONDS, Rank.TEN),
                                     new Card(Suit.HEARTS, Rank.TWO),
                                     new Card(Suit.DIAMONDS, Rank.KING),
                                     new Card(Suit.CLUBS, Rank.THREE),
                                     new Card(Suit.SPADES, Rank.ACE)
        );

        Game game = new Game(stubDeck);
        BlackjackController blackjackController = new BlackjackController(() -> game);
        blackjackController.startGame();
        blackjackController.hitCommand();

        Model model = new ConcurrentModel();
        String result = blackjackController.viewGame(model);
        assertThat(result).isEqualTo("blackjack");
    }

    @Test
    void testHitCommandAndBust() {
        Deck stubDeck = new StubDeck(new Card(Suit.DIAMONDS, Rank.TEN),
                                     new Card(Suit.HEARTS, Rank.TWO),
                                     new Card(Suit.DIAMONDS, Rank.KING),
                                     new Card(Suit.CLUBS, Rank.THREE),
                                     new Card(Suit.SPADES, Rank.FIVE)
        );

        Game game = new Game(stubDeck);
        BlackjackController blackjackController = new BlackjackController(() -> game);
        blackjackController.startGame();
        blackjackController.hitCommand();

        Model model = new ConcurrentModel();
        String result = blackjackController.viewGame(model);

        GameView gameView = (GameView) model.getAttribute("gameView");
        String outcome= (String) model.getAttribute("outcome");

        assertThat(gameView.getPlayerCards()).hasSize(3);
        assertThat(outcome).isEqualTo(GameOutcome.PLAYER_BUSTS.message());
        assertThat(result).isEqualTo("done");
    }

    @Test
    void testBlackjack() {
        Deck stubDeck = new StubDeck(new Card(Suit.DIAMONDS, Rank.QUEEN),
                                     new Card(Suit.HEARTS, Rank.TWO),
                                     new Card(Suit.DIAMONDS, Rank.ACE),
                                     new Card(Suit.CLUBS, Rank.THREE));

        Game game = new Game(stubDeck);
        BlackjackController blackjackController = new BlackjackController(() -> game);
        blackjackController.startGame();

        Model model = new ConcurrentModel();
        String result = blackjackController.viewGame(model);

        String outcome = (String) model.getAttribute("outcome");
        assertThat(outcome).isEqualTo("You won Blackjack!!! 💵💵💵💵");
        assertThat(result).isEqualTo("done");
    }

    @Test
    void testStandMeansPlayerIsDoneAndPlayerWins() {
        Deck stubDeck = new StubDeck(new Card(Suit.DIAMONDS, Rank.TEN), //player
                                     new Card(Suit.HEARTS, Rank.TWO),   //dealer
                                     new Card(Suit.DIAMONDS, Rank.KING), //player, stand at 20
                                     new Card(Suit.CLUBS, Rank.THREE), //dealer has 5
                                     new Card(Suit.CLUBS, Rank.EIGHT), //dealer has 13
                                     new Card(Suit.HEARTS, Rank.FOUR) //dealer has 17, MUST STAND
        );

        Game game = new Game(stubDeck);
        BlackjackController blackjackController = new BlackjackController(() -> game);
        blackjackController.startGame();
        blackjackController.standCommand();

        Model model = new ConcurrentModel();
        String result = blackjackController.viewGame(model); //we are mostly testing

        GameView gameView = (GameView) model.getAttribute("gameView");
        String outcome = (String) model.getAttribute("outcome");
        assertThat(gameView.getDealerCards()).hasSize(4);
        assertThat(outcome).isEqualTo("You beat the Dealer! 💵");
        assertThat(result).isEqualTo("done");
    }


    @Test
    void testStandMeansPlayerIsDoneAndDealerWins() {
        Deck stubDeck = new StubDeck(new Card(Suit.DIAMONDS, Rank.TEN), //player
                                     new Card(Suit.HEARTS, Rank.TWO),   //dealer
                                     new Card(Suit.DIAMONDS, Rank.KING), //player, stand at 20
                                     new Card(Suit.CLUBS, Rank.THREE), //dealer has 5
                                     new Card(Suit.CLUBS, Rank.EIGHT), //dealer has 13
                                     new Card(Suit.HEARTS, Rank.EIGHT) //dealer has 21, MUST STAND
        );

        Game game = new Game(stubDeck);
        BlackjackController blackjackController = new BlackjackController(() -> game);
        blackjackController.startGame();
        String result = blackjackController.standCommand();

        Model model = new ConcurrentModel();
        blackjackController.viewGame(model); //we are mostly testing

        GameView gameView = (GameView) model.getAttribute("gameView");
        String outcome = (String) model.getAttribute("outcome");
        assertThat(gameView.getDealerCards()).hasSize(4);
        assertThat(outcome).isEqualTo("You lost to the Dealer. 💸");
        assertThat(result).isEqualTo("redirect:/game");
    }
}
