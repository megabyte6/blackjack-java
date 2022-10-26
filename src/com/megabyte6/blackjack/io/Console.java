package com.megabyte6.blackjack.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.megabyte6.blackjack.player.Player;

public class Console {

    public static void clearScreen() {
        System.out.println("\033[H\033[2J");
        System.out.flush();
    }

    public static void printSeparator() {
        printSeparator(100);
    }

    public static void printSeparator(int length) {
        char[] separator = new char[length];
        Arrays.fill(separator, '-');

        System.out.println(new String(separator));
    }

    public static void printFile(String path) {
        Path filePath = Paths.get(path);
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(filePath);
        } catch (IOException e) {
            System.err.println(e);
            System.err.println("ERROR: Could not read '" + filePath + "'.");
            return;
        }
        for (String line : lines) {
            System.out.println(line);
        }
    }

    public static void printInfo(ArrayList<Player> players) {
        String hands = "| ";
        for (Player player : players) {
            hands = hands + player.getName() + ": " + player.getHandTotal() + " "
                    + player.getHand() + " | ";
        }
        printSeparator(hands.length());
        System.out.println(hands);
        printSeparator(hands.length());
    }

}
