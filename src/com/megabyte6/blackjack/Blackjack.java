package com.megabyte6.blackjack;

import static com.megabyte6.blackjack.io.Input.getInput;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import com.megabyte6.blackjack.io.Console;
import com.megabyte6.blackjack.player.Card;
import com.megabyte6.blackjack.player.Player;

public class Blackjack {

    private Scanner scanner;
    private ArrayList<Player> players;
    private HashSet<Card> availableCards = new HashSet<>();

    private boolean gameOver = false;
    private Player winner = null;

    public Blackjack(Scanner scanner) {
        this.scanner = scanner;
        this.players = new ArrayList<>();
        init();
    }

    public Blackjack(Scanner scanner, ArrayList<Player> players) {
        this.scanner = scanner;
        this.players = players;
        init();
    }

    private void init() {
        // Add one deck of cards to play with.
        for (Card card : Card.values()) {
            for (int i = 0; i < 4; i++) {
                availableCards.add(card);
            }
        }
        // Pre-draw the hands.
        for (Player player : players) {
            availableCards = player.drawCard(availableCards);
            availableCards = player.drawCard(availableCards);
        }
        // If dealer doesn't exist, create one.
        if (Player.getDealer(players) == null) {
            Player dealer = new Player("Dealer");
            players.add(dealer);
            Player.setDealer(dealer, players);
        }
        // Make the dealer's second card hidden.
        Player.getDealer(players).getHand().get(1).setHidden(true);
    }

    public void start() {
        while (!gameOver) {
            for (Player player : players) {
                if (player.isDealer()) {
                    dealerTurn();
                    if (isWinner(player, players)) {
                        System.out.println();
                        break;
                    }

                } else {
                    boolean playerTookTurn = playerTurn(player);

                    if (!playerTookTurn)
                        continue;

                    if (isWinner(player, players))
                        break;

                    System.out.print("\nPress enter to continue...");
                    scanner.nextLine();
                }
            }

            gameOver = true;
            for (Player player : players) {
                if (player.isPlaying())
                    gameOver = false;
            }
        }

        winner = findWinner(players);
        Player.won(winner);
        congratulateWinner(winner, players);

        System.out.println("\nPress enter to continue...");
        scanner.nextLine();
    }

    /**
     * @return {@code false} if the player's turn was skipped.
     */
    private boolean playerTurn(Player player) {
        if (!player.isPlaying())
            return false;

        Console.clearScreen();
        Console.printInfo(players);

        System.out.println("\nCurrent player: " + player.getName());
        System.out.println("Your current hand is " + player.getHand());
        System.out.println("Your current total is " + player.getHandTotal());
        System.out.println();
        String input = getInput(scanner, "Do you want to draw another card?", "hit", "stand");
        if (input.equals("hit")) {
            players = setPlayersToPlaying(players);

            availableCards = player.drawCard(availableCards);
            Card cardDrawn = player.getHand().get(player.getHand().size() - 1);
            System.out.println("You drew: " + cardDrawn);
            System.out.println("Your new total is " + player.getHandTotal());
        } else {
            player.setPlaying(false);
            System.out.println("You chose to stand. Wise choice.");
        }

        if (player.getHandTotal() > 21) {
            System.out.println("\nOh no! You're over 21!");
            System.out.println("You're out :(");
            player.setPlaying(false);
        }

        return true;
    }

    private void dealerTurn() {
        Player dealer = Player.getDealer(players);

        if (dealer.getHandTotal() >= 17) {
            dealer.setPlaying(false);
        }

        // Check if the dealer is still playing
        if (!dealer.isPlaying())
            return;

        // Check if it's the dealer's first turn.
        // If so, skip as the dealer is first in the list but last in a real game of blackjack.
        if (dealer.getHand().get(1).isHidden()) {
            dealer.getHand().get(1).setHidden(false);
            return;
        }

        availableCards = dealer.drawCard(availableCards);
    }

    private boolean isWinner(Player player, ArrayList<Player> players) {
        if (player.getHandTotal() == 21) {
            // Set all players to not playing.
            players.forEach(item -> item.setPlaying(false));
            return true;
        }
        return false;
    }

    private Player findWinner(ArrayList<Player> players) {
        Player winner = null;
        Player dealer = Player.getDealer(players);

        for (Player player : players) {
            if (player.isDealer())
                continue;
            if (player.getHandTotal() >= dealer.getHandTotal() && player.getHandTotal() <= 21)
                winner = player;
        }

        if (winner == null)
            winner = Player.getDealer(players);

        if (winner != dealer && winner.getHandTotal() == dealer.getHandTotal())
            winner = null;
        return winner;
    }

    private void congratulateWinner(Player winner, ArrayList<Player> players) {
        Console.clearScreen();
        Console.printInfo(players);
        String msg = winner.getHandTotal() == 21
                ? "Amazing! " + winner.getName() + " has won with a perfect score of "
                        + winner.getHandTotal() + "!\n"
                : "Nice one, " + winner.getName() + "! You've won with a score of "
                        + winner.getHandTotal() + "!\n";
        System.out.println(msg);
    }

    private ArrayList<Player> setPlayersToPlaying(ArrayList<Player> players) {
        ArrayList<Player> newPlayers = new ArrayList<>(players);
        for (Player player : newPlayers) {
            if (player.getHandTotal() <= 21)
                player.setPlaying(true);
        }
        return newPlayers;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

}
