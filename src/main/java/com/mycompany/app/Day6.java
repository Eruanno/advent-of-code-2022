package com.mycompany.app;

import java.io.IOException;
import java.util.List;

import static com.mycompany.app.FileReader.readInput;

class Day6 implements Day {
    private final String filename;
    private List<String> input;

    public Day6(String filename) throws IOException {
        this.filename = filename;
        loadData();
    }

    private void loadData() throws IOException {
        input = readInput(filename);
    }

    @Override
    public String calculateFirstStar() {
        return findMarker(4);
    }

    @Override
    public String calculateSecondStar() {
        return findMarker(14);
    }

    private String findMarker(int length) {
        String message = input.get(0);
        for (int marker = 0; marker < message.length() - length; marker++) {
            if (message.substring(marker, marker + length).chars()
                       .distinct()
                       .count() == length) {
                return "" + (marker + length);
            }
        }
        return "";
    }
}
