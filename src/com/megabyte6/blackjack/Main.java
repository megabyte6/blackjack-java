package com.megabyte6.blackjack;

import static com.megabyte6.blackjack.io.Input.getInput;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

import com.megabyte6.blackjack.io.Console;
import com.megabyte6.blackjack.player.Player;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        ArrayList<Player> players = welcomePlayers();

        String input;
        do {
            // Reset players.
            players.stream().forEach(p -> {
                p.clearHand();
                p.setPlaying(true);
            });

            Blackjack blackjack = new Blackjack(scanner, players);
            blackjack.start();

            Player winner = Player.getCurrentWinner();

            Console.clearScreen();
            Console.printInfo(players);

            if (winner == Player.getPreviousWinner()) {
                System.out.println("Wow! " + winner.getName() + " won again!");
            } else {
                System.out.println(winner.getName() + " won!");
            }

            input = getInput(scanner, "Do you want to play another game?", "yes", "no");
        } while (input.equals("yes"));

        System.out.println("\nScoreboard: ");
        players.stream()
                .sorted(Comparator.comparing(Player::getGamesWon))
                .forEach(p -> System.out.println(p.getName() + ": " + p.getGamesWon()));

        scanner.close();
    }

    private static ArrayList<Player> welcomePlayers() {
        ArrayList<Player> playersEntered = new ArrayList<>();
        boolean done = false;

        do {
            Console.clearScreen();
            Console.printFile("resources/Welcome.txt");
            Console.printSeparator();
            System.out.print("\nWhat's your name? ");
            playersEntered.add(new Player(scanner.nextLine()));

            String input = getInput(scanner, "\nAdd another player?", "yes", "no");
            switch (input) {
                case "no":
                    done = true;
                    break;
                case "yes":
                    break;
                default:
                    System.out.println("That's not a valid option. Please try again.");
                    break;
            }
        } while (!done);

        // Add dealer.
        Player dealer = new Player("Dealer");
        playersEntered.add(dealer);
        Player.setDealer(dealer, playersEntered);

        return playersEntered;
    }

}
