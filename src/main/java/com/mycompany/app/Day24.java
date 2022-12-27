package com.mycompany.app;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.mycompany.app.FileReader.readInput;
import static java.lang.Math.min;
import static java.util.stream.Collectors.toCollection;

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
            if (toVisit.size() > 1_000_000) {
                toVisit = toVisit.stream().sorted(((o1, o2) -> Integer.compare(width - o1.column * height - o1.row, width - o2.column * height - o2.row))).limit(200_000).collect(toCollection(LinkedList::new));
            }
            if (state.column == width - 2 && state.row == height - 1) {
                //System.out.println("End: %d\trow: %d\tcolumn:%d".formatted(state.step, state.row, state.column));
                results.add(state);
                minStep = min(minStep, state.step);
            } else if (state.step < minStep) {
                List<IBlizzard> blizzards = getBlizzardsFromCache(state.step + 1, toVisit.size());
                if (isFieldFree(state.row + 1, state.column, blizzards)) {
                    toVisit.offer(new State(state.row + 1, state.column, state.step + 1));
                }
                if (isFieldFree(state.row, state.column + 1, blizzards)) {
                    toVisit.offer(new State(state.row, state.column + 1, state.step + 1));
                }
                if (isFieldFree(state.row, state.column - 1, blizzards)) {
                    toVisit.offer(new State(state.row, state.column - 1, state.step + 1));
                }
                if (isFieldFree(state.row - 1, state.column, blizzards)) {
                    toVisit.offer(new State(state.row - 1, state.column, state.step + 1));
                }
                if (isFieldFree(state.row, state.column, blizzards)) {
                    toVisit.offer(new State(state.row, state.column, state.step + 1));
                }
            }
        }
        /*for(int i = 0; i < 20; i++) {
            List<IBlizzard> blizzards = cache.get(i);
            for(IBlizzard blizzard : blizzards) {
                System.out.print("[%d,%d,%c],".formatted(blizzard.row, blizzard.column, blizzard.direction));
            }
            System.out.println();
        }*/
        return "" + results.stream().mapToLong(State::step).min().getAsLong();
    }

    @Override
    public String calculateSecondStar() {
        return null;
    }

    private List<IBlizzard> getBlizzardsFromCache(int step, int size) {
        if (cache.containsKey(step)) {
            return cache.get(step);
        }
        List<IBlizzard> previous = cache.get(step - 1);
        List<IBlizzard> next = previous.stream().map(this::moveBlizzard).toList();
        cache.put(step, next);
        System.out.println(step + " " + size);
        return next;
    }

    private IBlizzard moveBlizzard(IBlizzard blizzard) {
        if (blizzard.direction == '^') {
            if (blizzard.row > 1) {
                return new IBlizzard(blizzard.row - 1, blizzard.column, blizzard.direction);
            } else {
                return new IBlizzard(height - 1, blizzard.column, blizzard.direction);
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
        if ((row == 0 && column == 1) || (row == height - 1 && column == width - 2)) {
            return true;
        }
        if (column < 1 || row < 1 || column > width - 1 || row > height - 1) {
            return false;
        }
        for (IBlizzard blizzard : blizzards) {
            if (blizzard.column == column && blizzard.row == row) {
                return false;
            }
        }
        return true;
    }

    private record State(int row, int column, int step) {
    }

    private record IBlizzard(int row, int column, char direction) {
    }
}
