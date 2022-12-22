package com.mycompany.app;

import java.io.IOException;
import java.util.List;

import static com.mycompany.app.FileReader.readInput;
import static java.lang.Character.isUpperCase;

class Day3 implements Day {

    private final String filename;
    private List<String> input;

    public Day3(String filename) throws IOException {
        this.filename = filename;
        loadData();
    }

    private void loadData() throws IOException {
        input = readInput(filename);
    }

    @Override
    public String calculateFirstStar() {
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
        return "" + totalPriority;
    }

    @Override
    public String calculateSecondStar() {
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
        return "" + totalPriority;
    }

    private int resolvePriority(char character) {
        if (isUpperCase(character)) {
            return character - 38;
        }
        return character - 96;
    }
}
