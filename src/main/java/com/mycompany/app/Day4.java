package com.mycompany.app;

import java.io.IOException;
import java.util.List;

import static com.mycompany.app.FileReader.readInput;
import static java.util.Arrays.stream;

class Day4 implements Day {

    private final String filename;
    private List<String> input;

    public Day4(String filename) {
        this.filename = filename;
    }

    @Override
    public void loadData() throws IOException {
        input = readInput(filename);
    }

    @Override
    public String calculateFirstStar() {
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
        return "" + accumulator;
    }

    @Override
    public String calculateSecondStar() {
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
        return "" + accumulator;
    }

    private int[] getSection(String section) {
        return stream(section.split("-")).mapToInt(Integer::parseInt).toArray();
    }
}
