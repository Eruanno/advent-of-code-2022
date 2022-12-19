package com.mycompany.app;

import java.io.IOException;
import java.util.List;

import static com.mycompany.app.FileReader.readInput;
import static com.mycompany.app.Logger.log;

public class Day10 implements Day {

    private int[] history;

    public void solve() throws IOException {
        List<String> input = readInput("day-10");
        prepareData(input);
        log("Day 10:");
        log("First star:");
        log(calculateFirstStar());
        log("Second star:");
        calculateSecondStar();
    }

    private void prepareData(List<String> input) {
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

    private Long calculateFirstStar() {
        return 20L * history[19] + 60L * history[59] + 100L * history[99] + 140L * history[139] + 180L * history[179] + 220L * history[219];
    }

    private void calculateSecondStar() {
        for (int h = 0; h < 6; h++) {
            for (int w = 0; w < 40; w++) {
                if (w >= history[h * 40 + w] - 1 && w <= history[h * 40 + w] + 1) {
                    System.out.print('#');
                } else {
                    System.out.print('-');
                }
            }
            System.out.println();
        }
    }
}