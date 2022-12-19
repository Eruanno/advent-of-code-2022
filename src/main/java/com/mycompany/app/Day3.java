package com.mycompany.app;

import java.io.IOException;
import java.util.List;

import static com.mycompany.app.FileReader.readInput;
import static com.mycompany.app.Logger.log;
import static java.lang.Character.isUpperCase;

class Day3 implements Day {

    public void solve() throws IOException {
        List<String> input = readInput("day-3");
        log("Day 3:");
        log("First star:");
        log(calculateFirstStar(input));
        log("Second star:");
        log(calculateSecondStar(input));
    }

    private Long calculateFirstStar(List<String> input) {
        long totalPriority = 0;
        for (String rucksack : input) {
            String compartmentA = rucksack.substring(0, (rucksack.length()) / 2);
            String compartmentB = rucksack.substring((rucksack.length()) / 2);
            char priorityLetter = 'a';
            for (int i = 0; i < compartmentA.length(); i++) {
                for (int j = 0; j < compartmentB.length(); j++) {
                    if (compartmentA.charAt(i) == compartmentB.charAt(j)) {
                        priorityLetter = compartmentA.charAt(i);
                        break;
                    }
                }
            }
            totalPriority += resolvePriority(priorityLetter);
        }
        return totalPriority;
    }

    private Long calculateSecondStar(List<String> input) {
        long totalPriority = 0;
        for (int g = 0; g < input.size() - 2; g += 3) {
            String rucksackA = input.get(g);
            String rucksackB = input.get(g + 1);
            String rucksackC = input.get(g + 2);
            char priorityLetter = 'a';
            for (int i = 0; i < rucksackA.length(); i++) {
                for (int j = 0; j < rucksackB.length(); j++) {
                    for (int k = 0; k < rucksackC.length(); k++) {
                        if (rucksackA.charAt(i) == rucksackB.charAt(j) && rucksackB.charAt(j) == rucksackC.charAt(k)) {
                            priorityLetter = rucksackA.charAt(i);
                            break;
                        }
                    }
                }
            }
            totalPriority += resolvePriority(priorityLetter);
        }
        return totalPriority;
    }

    private int resolvePriority(char character) {
        if (isUpperCase(character)) {
            return character - 38;
        }
        return character - 96;
    }
}
