package com.mycompany.app;

import java.io.IOException;
import java.util.List;

import static com.mycompany.app.FileReader.readInput;
import static com.mycompany.app.Logger.log;

class Day2 implements Day {

    public void solve() throws IOException {
        List<String> input = readInput("day-2");
        log("Day 2:");
        log("First star:");
        log(calculateFirstStar(input));
        log("Second star:");
        log(calculateSecondStar(input));
    }

    /**
     * A, X for Rock
     * B, Y for Paper
     * C, Z for Scissors
     */
    private Long calculateFirstStar(List<String> input) {
        long totalPoints = 0;
        for (String game : input) {
            switch (game) {
                case "A X" -> totalPoints += 1 + 3;
                case "A Y" -> totalPoints += 2 + 6;
                case "A Z" -> totalPoints += 3 + 0;
                case "B X" -> totalPoints += 1 + 0;
                case "B Y" -> totalPoints += 2 + 3;
                case "B Z" -> totalPoints += 3 + 6;
                case "C X" -> totalPoints += 1 + 6;
                case "C Y" -> totalPoints += 2 + 0;
                case "C Z" -> totalPoints += 3 + 3;
                default -> {
                }
            }
        }
        return totalPoints;
    }

    /**
     * A for Rock - 1
     * B for Paper - 2
     * C for Scissors - 3
     * X lose - 0
     * Y draw - 3
     * Z win - 6
     */
    private Long calculateSecondStar(List<String> input) {
        long totalPoints = 0;
        for (String game : input) {
            switch (game) {
                case "A X" -> totalPoints += 0 + 3;
                case "A Y" -> totalPoints += 3 + 1;
                case "A Z" -> totalPoints += 6 + 2;
                case "B X" -> totalPoints += 0 + 1;
                case "B Y" -> totalPoints += 3 + 2;
                case "B Z" -> totalPoints += 6 + 3;
                case "C X" -> totalPoints += 0 + 2;
                case "C Y" -> totalPoints += 3 + 3;
                case "C Z" -> totalPoints += 6 + 1;
                default -> {
                }
            }
        }
        return totalPoints;
    }
}
