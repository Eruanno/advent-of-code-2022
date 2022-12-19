package com.mycompany.app;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.mycompany.app.FileReader.readInput;
import static com.mycompany.app.Logger.log;
import static java.util.Arrays.stream;

class Day4 implements Day {

    public void solve() throws IOException {
        List<String> input = readInput("day-4");
        log("Day 4:");
        log("First star:");
        log(calculateFirstStar(input));
        log("Second star:");
        log(calculateSecondStar(input));
    }

    private Long calculateFirstStar(List<String> input) {
        long accumulator = 0;
        for (String pair : input) {
            String[] sections = pair.split(",");
            int[] sectionA = getSection(sections[0]);
            int[] sectionB = getSection(sections[1]);
            if (sectionA[0] >= sectionB[0] && sectionA[1] <= sectionB[1]) {
                accumulator++;
            } else if (sectionB[0] >= sectionA[0] && sectionB[1] <= sectionA[1]) {
                accumulator++;
            }
        }
        return accumulator;
    }

    private Long calculateSecondStar(List<String> input) {
        long accumulator = 0;
        for (String pair : input) {
            String[] sections = pair.split(",");
            int[] sectionA = getSection(sections[0]);
            int[] sectionB = getSection(sections[1]);
            if ((sectionA[0] <= sectionB[0] && sectionA[1] >= sectionB[0]) || (sectionA[0] <= sectionB[1] && sectionA[1] >= sectionB[1])) {
                accumulator++;
            } else if ((sectionB[0] <= sectionA[0] && sectionB[1] >= sectionA[0]) || (sectionB[0] <= sectionA[1] && sectionB[1] >= sectionA[1])) {
                accumulator++;
            }
        }
        return accumulator;
    }

    private int[] getSection(String section) {
        return stream(section.split("-")).mapToInt(Integer::parseInt).toArray();
    }
}
