package com.jitterted.ebp.blackjack.domain;

import java.util.Arrays;
import java.util.Iterator;

public class StubDeck extends Deck {
    private final Iterator<Card> iterator;

    public StubDeck(Card... cards) {
        this.iterator = Arrays.stream(cards).iterator();
    }

    @Override
    public Card draw() {
        return iterator.next();
    }
}
