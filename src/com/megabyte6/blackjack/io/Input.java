package com.megabyte6.blackjack.io;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Input {

    public static String getInput(Scanner scanner, String question, String... acceptableInput) {
        return getInput(scanner, question, false, false, acceptableInput);
    }

    public static String getInput(Scanner scanner, String question, boolean caseSensitive, String... acceptableInput) {
        return getInput(scanner, question, caseSensitive, false, acceptableInput);
    }

    public static String getInput(Scanner scanner, String question, boolean caseSensitive, boolean newLineAfterQuestion,
            String... acceptableInput) {
        boolean inputValid = false;
        String input;
        ArrayList<String> inputOptions = new ArrayList<>(asList(acceptableInput));

        if (!caseSensitive) {
            inputOptions = new ArrayList<>(inputOptions.stream()
                    .map(String::toLowerCase)
                    .collect(Collectors.toList()));
        }

        // Add accepted input to the question.
        question += " (" + String.join(", ", inputOptions) + "): ";

        if (newLineAfterQuestion)
            question += "\n";

        do {
            System.out.print(question);
            input = scanner.nextLine().trim();

            if (!caseSensitive)
                input = input.toLowerCase();

            if (inputOptions.contains(input)) {
                inputValid = true;
            } else {
                System.out.println("That's not one of the options. Please check your spelling and try again.");
            }
        } while (!inputValid);

        return input;
    }

}
