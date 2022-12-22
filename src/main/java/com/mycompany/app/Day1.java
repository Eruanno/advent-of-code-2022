package com.mycompany.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.mycompany.app.FileReader.readInput;
import static java.util.Comparator.reverseOrder;

class Day1 implements Day {

    private final List<Long> totalCalories = new ArrayList<>();
    private final String filename;
    private List<String> input;

    public Day1(String filename) throws IOException {
        this.filename = filename;
        loadData();
    }

    private void loadData() throws IOException {
        input = readInput(filename);
        prepareData();
    }

    private void prepareData() {
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

    @Override
    public String calculateFirstStar() {
        return "" + totalCalories.stream().max(Long::compareTo).orElse(-1L);
    }

    @Override
    public String calculateSecondStar() {
        return "" + totalCalories.stream()
                                 .sorted(reverseOrder())
                                 .limit(3)
                                 .mapToLong(Long::valueOf)
                                 .sum();
    }
}
