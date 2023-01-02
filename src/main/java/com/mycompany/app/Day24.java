package com.mycompany.app;

import java.io.IOException;
import java.util.*;

import static com.mycompany.app.FileReader.readInput;
import static java.lang.Math.min;

public class Day24 implements Day {

    private final Map<Integer, List<Blizzard>> cache = new HashMap<>();
    private final String filename;
    private List<String> input;
    private int width = 0;
    private int height = 0;

    public Day24(String filename) throws IOException {
        this.filename = filename;
        loadData();
    }

    private void loadData() throws IOException {
        input = readInput(filename);
        prepareData();
    }

    private void prepareData() {
        height = input.size();
        width = input.get(0).length();
        List<Blizzard> blizzards = new ArrayList<>();
        for (int y = 0; y < input.size(); y++) {
            String line = input.get(y);
            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);
                if (c == '>' || c == 'v' || c == '<' || c == '^') {
                    blizzards.add(new Blizzard(y, x, c));
                }
            }
        }
        cache.put(0, blizzards);
    }

    @Override
    public String calculateFirstStar() {
        return "" + moveThroughValley(0, 1, 0, height - 1, width - 2);
    }

    @Override
    public String calculateSecondStar() {
        int journey = moveThroughValley(0, 1, 0, height - 1, width - 2);
        int back = moveThroughValley(height - 1, width - 2, journey, 0, 1);
        int andThereAgain = moveThroughValley(0, 1, back, height - 1, width - 2);
        return "" + andThereAgain;
    }

    private Integer moveThroughValley(int startRow, int startColumn, int step, int endRow, int endColumn) {
        Queue<State> queue = new LinkedList<>();
        queue.add(new State(startRow, startColumn, step));
        int minStep = Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
            State state = queue.poll();
            if (state.column == endColumn && state.row == endRow) {
                minStep = min(minStep, state.step);
            } else if (state.step < minStep) {
                int nextStep = state.step + 1;
                List<Blizzard> blizzards = getBlizzardsFromCache(nextStep, queue.size());
                if (isFieldFree(state.row + 1, state.column, blizzards)) {
                    State nextState = new State(state.row + 1, state.column, nextStep);
                    if (!queue.contains(nextState)) {
                        queue.offer(nextState);
                    }
                }
                if (isFieldFree(state.row, state.column + 1, blizzards)) {
                    State nextState = new State(state.row, state.column + 1, nextStep);
                    if (!queue.contains(nextState)) {
                        queue.offer(nextState);
                    }
                }
                if (isFieldFree(state.row, state.column - 1, blizzards)) {
                    State nextState = new State(state.row, state.column - 1, nextStep);
                    if (!queue.contains(nextState)) {
                        queue.offer(nextState);
                    }
                }
                if (isFieldFree(state.row - 1, state.column, blizzards)) {
                    State nextState = new State(state.row - 1, state.column, nextStep);
                    if (!queue.contains(nextState)) {
                        queue.offer(nextState);
                    }
                }
                if (isFieldFree(state.row, state.column, blizzards)) {
                    State nextState = new State(state.row, state.column, nextStep);
                    if (!queue.contains(nextState)) {
                        queue.offer(nextState);
                    }
                }
            }
        }
        return minStep;
    }

    private List<Blizzard> getBlizzardsFromCache(int step, int size) {
        if (cache.containsKey(step)) {
            return cache.get(step);
        }
        List<Blizzard> previous = cache.get(step - 1);
        List<Blizzard> next = previous.stream().map(this::moveBlizzard).toList();
        cache.put(step, next);
        return next;
    }

    private Blizzard moveBlizzard(Blizzard blizzard) {
        if (blizzard.direction == '^') {
            if (blizzard.row > 1) {
                return new Blizzard(blizzard.row - 1, blizzard.column, blizzard.direction);
            } else {
                return new Blizzard(height - 2, blizzard.column, blizzard.direction);
            }
        }
        if (blizzard.direction == '>') {
            if (blizzard.column < width - 2) {
                return new Blizzard(blizzard.row, blizzard.column + 1, blizzard.direction);
            } else {
                return new Blizzard(blizzard.row, 1, blizzard.direction);
            }
        }
        if (blizzard.direction == 'v') {
            if (blizzard.row < height - 2) {
                return new Blizzard(blizzard.row + 1, blizzard.column, blizzard.direction);

            } else {
                return new Blizzard(1, blizzard.column, blizzard.direction);
            }
        }
        if (blizzard.direction == '<') {
            if (blizzard.column > 1) {
                return new Blizzard(blizzard.row, blizzard.column - 1, blizzard.direction);
            } else {
                return new Blizzard(blizzard.row, width - 2, blizzard.direction);
            }
        }
        throw new IllegalArgumentException("Blizzard is broken.");
    }

    private boolean isFieldFree(int row, int column, List<Blizzard> blizzards) {
        if ((row == 0 && column == 1) || (row == height - 1 && column == width - 2)) { // Start / end.
            return true;
        }
        if (column < 1 || row < 1 || column > width - 2 || row > height - 2) { // Border.
            return false;
        }
        for (Blizzard blizzard : blizzards) {
            if (blizzard.column == column && blizzard.row == row) { // Blizzard.
                return false;
            }
        }
        return true;
    }

    private record State(int row, int column, int step) {
    }

    private record Blizzard(int row, int column, char direction) {
    }
}
