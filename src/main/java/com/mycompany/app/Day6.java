package com.mycompany.app;

import java.io.IOException;
import java.util.List;

import static com.mycompany.app.FileReader.readInput;
import static com.mycompany.app.Logger.log;

class Day6 implements Day {

    public void solve() throws IOException {
        List<String> input = readInput("day-6");
        log("Day 6:");
        log("First star:");
        log(calculateFirstStar(input));
        log("Second star:");
        log(calculateSecondStar(input));
    }

    private Long calculateFirstStar(List<String> input) {
        return findMarker(input.get(0), 4);
    }

    private Long calculateSecondStar(List<String> input) {
        return findMarker(input.get(0), 14);
    }

    private Long findMarker(String message, int length) {
        for (int marker = 0; marker < message.length() - length; marker++) {
            if (message.substring(marker, marker + length).chars()
                       .distinct()
                       .count() == length) {
                return (long) (marker + length);
            }
        }
        return -1L;
    }
}
