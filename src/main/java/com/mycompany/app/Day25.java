package com.mycompany.app;

import java.io.IOException;
import java.util.List;

import static com.mycompany.app.FileReader.readInput;

public class Day25 implements Day {

    private final String filename;
    private List<String> input;

    public Day25(String filename) throws IOException {
        this.filename = filename;
        loadData();
    }

    private void loadData() throws IOException {
        input = readInput(filename);
        prepareData();
    }

    private void prepareData() {

    }

    @Override
    public String calculateFirstStar() {
        return null;
    }

    @Override
    public String calculateSecondStar() {
        return null;
    }
}
