package com.mycompany.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.mycompany.app.FileReader.readInput;
import static com.mycompany.app.Logger.log;
import static java.util.Comparator.reverseOrder;

class Day1 implements Day {

    private final List<Long> totalCalories = new ArrayList<>();

    public void solve() throws IOException {
        List<String> input = readInput("day-1");
        prepareData(input);
        log("Day 1:");
        log("First star:");
        log(calculateFirstStar());
        log("Second star:");
        log(calculateSecondStar());
    }

    private void prepareData(List<String> input) {
        List<Long> calories = new ArrayList<>();
        for (String line : input) {
            if (line.isEmpty()) {
                totalCalories.add(calories.stream().mapToLong(Long::valueOf).sum());
                calories = new ArrayList<>();
            } else {
                calories.add(Long.parseLong(line));
            }
        }
    }

    private Long calculateFirstStar() {
        return totalCalories.stream().max(Long::compareTo).orElse(-1L);
    }

    private Long calculateSecondStar() {
        return totalCalories.stream()
                                 .sorted(reverseOrder())
                                 .limit(3)
                                 .mapToLong(Long::valueOf)
                                 .sum();
    }
}
