package com.megabyte6.blackjack.player;

import java.util.Random;

public enum Card {

    ACE, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING;

    private boolean hidden = false;

    public int value() {
        if (this.hidden)
            return 0;

        return switch (this) {
            case ACE -> 0;
            case ONE -> 1;
            case TWO -> 2;
            case THREE -> 3;
            case FOUR -> 4;
            case FIVE -> 5;
            case SIX -> 6;
            case SEVEN -> 7;
            case EIGHT -> 8;
            case NINE -> 9;
            case TEN, JACK, QUEEN, KING -> 10;
        };
    }

    public String toString() {
        if (this.hidden)
            return "?";

        return switch (this) {
            case ACE -> "Ace";
            case ONE -> "One";
            case TWO -> "Two";
            case THREE -> "Three";
            case FOUR -> "Four";
            case FIVE -> "Five";
            case SIX -> "Six";
            case SEVEN -> "Seven";
            case EIGHT -> "Eight";
            case NINE -> "Nine";
            case TEN -> "Ten";
            case JACK -> "Jack";
            case QUEEN -> "Queen";
            case KING -> "King";
        };
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public static Card randomCard() {
        final Random RANDOM = new Random();
        final Card[] CARDS = Card.values();
        final int SIZE = CARDS.length;
        return CARDS[RANDOM.nextInt(SIZE)];
    }

    public static int randomCardValue() {
        return randomCard().value();
    }

}
