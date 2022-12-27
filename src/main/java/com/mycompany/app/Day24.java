package com.mycompany.app;

import java.io.IOException;
import java.util.*;

import static com.mycompany.app.FileReader.readInput;
import static java.lang.Math.min;

public class Day24 implements Day {

    private final String filename;
    private final Map<Integer, List<IBlizzard>> cache = new HashMap<>();
    private List<String> input;
    private int width = 0;
    private int height = 0;

    public static void main(String[] args) throws IOException {
        new Day24("day-24").calculateFirstStar();
    }

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
        List<IBlizzard> b = new ArrayList<>();
        for (int y = 0; y < input.size(); y++) {
            String line = input.get(y);
            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);
                if (c == '>' || c == 'v' || c == '<' || c == '^') {
                    //blizzards.add(new Blizzard(y, x, c));
                    b.add(new IBlizzard(y, x, c));
                }
            }
        }
        cache.put(0, b);
    }

    @Override
    public String calculateFirstStar() {
        Queue<State> toVisit = new LinkedList<>();
        toVisit.add(new State(0, 1, 0));
        Set<State> results = new HashSet<>();
        int minStep = Integer.MAX_VALUE;
        while (!toVisit.isEmpty()) {
            State state = toVisit.poll();
            if (state.column == width - 2 && state.row == height - 1) {
                results.add(state);
                minStep = min(minStep, state.step);
            } else if (state.step < minStep) {
                int nextStep = state.step + 1;
                List<IBlizzard> blizzards = getBlizzardsFromCache(nextStep, toVisit.size());
                if (isFieldFree(state.row + 1, state.column, blizzards)) {
                    State nextState = new State(state.row + 1, state.column, nextStep);
                    if (!toVisit.contains(nextState)) {
                        toVisit.offer(nextState);
                    }
                }
                if (isFieldFree(state.row, state.column + 1, blizzards)) {
                    State nextState = new State(state.row, state.column + 1, nextStep);
                    if (!toVisit.contains(nextState)) {
                        toVisit.offer(nextState);
                    }
                }
                if (isFieldFree(state.row, state.column - 1, blizzards)) {
                    State nextState = new State(state.row, state.column - 1, nextStep);
                    if (!toVisit.contains(nextState)) {
                        toVisit.offer(nextState);
                    }
                }
                if (isFieldFree(state.row - 1, state.column, blizzards)) {
                    State nextState = new State(state.row - 1, state.column, nextStep);
                    if (!toVisit.contains(nextState)) {
                        toVisit.offer(nextState);
                    }
                }
                if (isFieldFree(state.row, state.column, blizzards)) {
                    State nextState = new State(state.row, state.column, nextStep);
                    if (!toVisit.contains(nextState)) {
                        toVisit.offer(nextState);
                    }
                }
            }
        }
        return "" + results.stream().mapToLong(State::step).min().getAsLong();
    }

    @Override
    public String calculateSecondStar() {
        Queue<State> toVisit = new LinkedList<>();
        toVisit.add(new State(0, 1, 0));
        Set<State> results = new HashSet<>();
        int minStep = Integer.MAX_VALUE;
        while (!toVisit.isEmpty()) {
            State state = toVisit.poll();
            if (state.column == width - 2 && state.row == height - 1) {
                results.add(state);
                minStep = min(minStep, state.step);
            } else if (state.step < minStep) {
                int nextStep = state.step + 1;
                List<IBlizzard> blizzards = getBlizzardsFromCache(nextStep, toVisit.size());
                if (isFieldFree(state.row + 1, state.column, blizzards)) {
                    State nextState = new State(state.row + 1, state.column, nextStep);
                    if (!toVisit.contains(nextState)) {
                        toVisit.offer(nextState);
                    }
                }
                if (isFieldFree(state.row, state.column + 1, blizzards)) {
                    State nextState = new State(state.row, state.column + 1, nextStep);
                    if (!toVisit.contains(nextState)) {
                        toVisit.offer(nextState);
                    }
                }
                if (isFieldFree(state.row, state.column - 1, blizzards)) {
                    State nextState = new State(state.row, state.column - 1, nextStep);
                    if (!toVisit.contains(nextState)) {
                        toVisit.offer(nextState);
                    }
                }
                if (isFieldFree(state.row - 1, state.column, blizzards)) {
                    State nextState = new State(state.row - 1, state.column, nextStep);
                    if (!toVisit.contains(nextState)) {
                        toVisit.offer(nextState);
                    }
                }
                if (isFieldFree(state.row, state.column, blizzards)) {
                    State nextState = new State(state.row, state.column, nextStep);
                    if (!toVisit.contains(nextState)) {
                        toVisit.offer(nextState);
                    }
                }
            }
        }
        long first = results.stream().mapToLong(State::step).min().getAsLong();
        toVisit = new LinkedList<>();
        toVisit.add(new State(height - 1, width - 2, (int) first));
        results = new HashSet<>();
        minStep = Integer.MAX_VALUE;
        while (!toVisit.isEmpty()) {
            State state = toVisit.poll();
            if (state.column == 1 && state.row == 0) {
                results.add(state);
                minStep = min(minStep, state.step);
            } else if (state.step < minStep) {
                int nextStep = state.step + 1;
                List<IBlizzard> blizzards = getBlizzardsFromCache(nextStep, toVisit.size());
                if (isFieldFree(state.row + 1, state.column, blizzards)) {
                    State nextState = new State(state.row + 1, state.column, nextStep);
                    if (!toVisit.contains(nextState)) {
                        toVisit.offer(nextState);
                    }
                }
                if (isFieldFree(state.row, state.column + 1, blizzards)) {
                    State nextState = new State(state.row, state.column + 1, nextStep);
                    if (!toVisit.contains(nextState)) {
                        toVisit.offer(nextState);
                    }
                }
                if (isFieldFree(state.row, state.column - 1, blizzards)) {
                    State nextState = new State(state.row, state.column - 1, nextStep);
                    if (!toVisit.contains(nextState)) {
                        toVisit.offer(nextState);
                    }
                }
                if (isFieldFree(state.row - 1, state.column, blizzards)) {
                    State nextState = new State(state.row - 1, state.column, nextStep);
                    if (!toVisit.contains(nextState)) {
                        toVisit.offer(nextState);
                    }
                }
                if (isFieldFree(state.row, state.column, blizzards)) {
                    State nextState = new State(state.row, state.column, nextStep);
                    if (!toVisit.contains(nextState)) {
                        toVisit.offer(nextState);
                    }
                }
            }
        }
        long back = results.stream().mapToLong(State::step).min().getAsLong();
        toVisit = new LinkedList<>();
        toVisit.add(new State(0, 1, (int) (back)));
        results = new HashSet<>();
        minStep = Integer.MAX_VALUE;
        while (!toVisit.isEmpty()) {
            State state = toVisit.poll();
            if (state.column == width - 2 && state.row == height - 1) {
                results.add(state);
                minStep = min(minStep, state.step);
            } else if (state.step < minStep) {
                int nextStep = state.step + 1;
                List<IBlizzard> blizzards = getBlizzardsFromCache(nextStep, toVisit.size());
                if (isFieldFree(state.row + 1, state.column, blizzards)) {
                    State nextState = new State(state.row + 1, state.column, nextStep);
                    if (!toVisit.contains(nextState)) {
                        toVisit.offer(nextState);
                    }
                }
                if (isFieldFree(state.row, state.column + 1, blizzards)) {
                    State nextState = new State(state.row, state.column + 1, nextStep);
                    if (!toVisit.contains(nextState)) {
                        toVisit.offer(nextState);
                    }
                }
                if (isFieldFree(state.row, state.column - 1, blizzards)) {
                    State nextState = new State(state.row, state.column - 1, nextStep);
                    if (!toVisit.contains(nextState)) {
                        toVisit.offer(nextState);
                    }
                }
                if (isFieldFree(state.row - 1, state.column, blizzards)) {
                    State nextState = new State(state.row - 1, state.column, nextStep);
                    if (!toVisit.contains(nextState)) {
                        toVisit.offer(nextState);
                    }
                }
                if (isFieldFree(state.row, state.column, blizzards)) {
                    State nextState = new State(state.row, state.column, nextStep);
                    if (!toVisit.contains(nextState)) {
                        toVisit.offer(nextState);
                    }
                }
            }
        }
        long andThereAgain = results.stream().mapToLong(State::step).min().getAsLong();
        return "" + andThereAgain;
    }

    private List<IBlizzard> getBlizzardsFromCache(int step, int size) {
        if (cache.containsKey(step)) {
            return cache.get(step);
        }
        List<IBlizzard> previous = cache.get(step - 1);
        List<IBlizzard> next = previous.stream().map(this::moveBlizzard).toList();
        cache.put(step, next);
        //System.out.println(step + " " + size);
        return next;
    }

    private IBlizzard moveBlizzard(IBlizzard blizzard) {
        if (blizzard.direction == '^') {
            if (blizzard.row > 1) {
                return new IBlizzard(blizzard.row - 1, blizzard.column, blizzard.direction);
            } else {
                return new IBlizzard(height - 2, blizzard.column, blizzard.direction);
            }
        }
        if (blizzard.direction == '>') {
            if (blizzard.column < width - 2) {
                return new IBlizzard(blizzard.row, blizzard.column + 1, blizzard.direction);
            } else {
                return new IBlizzard(blizzard.row, 1, blizzard.direction);
            }
        }
        if (blizzard.direction == 'v') {
            if (blizzard.row < height - 2) {
                return new IBlizzard(blizzard.row + 1, blizzard.column, blizzard.direction);

            } else {
                return new IBlizzard(1, blizzard.column, blizzard.direction);
            }
        }
        if (blizzard.direction == '<') {
            if (blizzard.column > 1) {
                return new IBlizzard(blizzard.row, blizzard.column - 1, blizzard.direction);
            } else {
                return new IBlizzard(blizzard.row, width - 2, blizzard.direction);
            }
        }
        throw new IllegalArgumentException("Blizzard is broken.");
    }

    private boolean isFieldFree(int row, int column, List<IBlizzard> blizzards) {
        if ((row == 0 && column == 1) || (row == height - 1 && column == width - 2)) { // start / end
            return true;
        }
        if (column < 1 || row < 1 || column > width - 2 || row > height - 2) { // border
            return false;
        }
        for (IBlizzard blizzard : blizzards) {
            if (blizzard.column == column && blizzard.row == row) { // blizzard
                return false;
            }
        }
        return true;
    }

    private void displayMap(State state) {
        List<IBlizzard> blizzards = cache.get(state.step);
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                if (r == state.row && c == state.column) {
                    System.out.print('E');
                } else if ((r == 0 && c == 1) || (r == height - 1 && c == width - 2)) {
                    System.out.print('.');
                } else if (c < 1 || r < 1 || c >= width - 1 || r >= height - 1) {
                    System.out.print('#');
                } else {
                    boolean b = false;
                    for (IBlizzard blizzard : blizzards) {
                        if (blizzard.column == c && blizzard.row == r) {
                            System.out.print('X');
                            b = true;
                            break;
                        }
                    }
                    if (!b) {
                        System.out.print('.');
                    }
                }
            }
            System.out.println();
        }
    }

    private record State(int row, int column, int step) {
    }

    private record IBlizzard(int row, int column, char direction) {
    }
}
