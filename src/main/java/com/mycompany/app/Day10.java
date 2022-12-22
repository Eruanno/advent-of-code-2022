package com.mycompany.app;

import java.io.IOException;
import java.util.List;

import static com.mycompany.app.FileReader.readInput;

public class Day10 implements Day {

    private int[] history;

    private final String filename;
    private List<String> input;

    public Day10(String filename) throws IOException {
        this.filename = filename;
        loadData();
    }

    private void loadData() throws IOException {
        input = readInput(filename);
        prepareData();
    }

    private void prepareData() {
        history = new int[input.size() * 2];
        int cycle = 0;
        int register = 1;
        for (String command : input) {
            if ("noop".equals(command)) {
                cycle++;
                history[cycle] = register;
            } else {
                cycle++;
                history[cycle] = register;
                cycle++;
                register += Integer.parseInt(command.split(" ")[1]);
                history[cycle] = register;
            }
        }
    }

    @Override
    public String calculateFirstStar() {
        return "" + (20L * history[19] + 60L * history[59] + 100L * history[99] + 140L * history[139] + 180L * history[179] + 220L * history[219]);
    }

    @Override
    public String calculateSecondStar() {
        StringBuilder result = new StringBuilder();
        for (int h = 0; h < 6; h++) {
            for (int w = 0; w < 40; w++) {
                if (w >= history[h * 40 + w] - 1 && w <= history[h * 40 + w] + 1) {
                    result.append('#');
                } else {
                    result.append('.');
                }
            }
            result.append("\n");
        }
        return result.toString();
    }
}
