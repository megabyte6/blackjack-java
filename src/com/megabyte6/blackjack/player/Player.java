package com.megabyte6.blackjack.player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Player {

    private static Player currentWinner = null;
    private static Player previousWinner = null;

    private String name;
    private ArrayList<Card> hand = new ArrayList<>();
    private int gamesWon = 0;

    private boolean playing = true;
    private boolean isDealer = false;

    public Player(String name) {
        this.name = name;
    }

    public Player(String name, ArrayList<Card> hand) {
        this.name = name;
        this.hand = hand;
    }

    public Player(String name, ArrayList<Card> hand, int gamesWon) {
        this.name = name;
        this.hand = hand;
        this.gamesWon = gamesWon;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashSet<Card> drawCard(HashSet<Card> availableCards) {
        if (availableCards.size() < 1)
            return null;

        Card randomCard;
        do {
            randomCard = Card.randomCard();
        } while (!availableCards.contains(randomCard));

        addCardToHand(randomCard);

        HashSet<Card> newAvailableCards = new HashSet<>(availableCards);
        newAvailableCards.remove(randomCard);
        return newAvailableCards;
    }

    public void addCardToHand(Card card) {
        this.hand.add(card);
    }

    public ArrayList<Card> getHand() {
        return this.hand;
    }

    public int getHandTotal() {
        int total = 0;

        for (Card card : this.hand) {
            // Ace value is 0.
            total += card.value();
        }
        // Check ace cards.
        for (Card card : this.hand) {
            if (card != Card.ACE)
                continue;
            if (total > 10) {
                total += 1;
            } else {
                total += 11;
            }
        }

        return total;
    }

    public void clearHand() {
        this.hand.clear();
    }

    public int getGamesWon() {
        return this.gamesWon;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public boolean isPlaying() {
        return this.playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public boolean isDealer() {
        return this.isDealer;
    }

    public static Player getDealer(List<Player> players) {
        for (Player player : players) {
            if (player.isDealer == true)
                return player;
        }
        return null;
    }

    public static Player setDealer(Player player, List<Player> players) {
        Player dealer = Player.getDealer(players);

        // Clear old dealer.
        if (dealer != null)
            dealer.isDealer = false;

        player.isDealer = true;

        return dealer;
    }

    public static Player getCurrentWinner() {
        return Player.currentWinner;
    }

    public static Player getPreviousWinner() {
        return Player.previousWinner;
    }

    public static void won(Player winner) {
        winner.gamesWon++;
        Player.previousWinner = currentWinner;
        Player.currentWinner = winner;
    }

}
