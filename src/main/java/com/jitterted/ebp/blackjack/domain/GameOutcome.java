package com.jitterted.ebp.blackjack.domain;

public enum GameOutcome {
    DEALER_BUSTS("Dealer went BUST, Player wins! Yay for you!! 💵"),
    PLAYER_BEATS_DEALER("You beat the Dealer! 💵"),
    PLAYER_PUSHES_DEALER("Push: Nobody wins, we'll call it even."),
    PLAYER_LOSES("You lost to the Dealer. 💸"),
    PLAYER_BUSTS("You Busted, so you lose.  💸"),
    BLACKJACK("You won Blackjack!!! 💵💵💵💵");

    private String message;

    GameOutcome(String message) {
        this.message = message;
    }
    public String message() {
        return message;
    }
}

