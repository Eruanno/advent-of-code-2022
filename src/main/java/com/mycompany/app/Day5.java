package com.mycompany.app;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mycompany.app.FileReader.readInput;
import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

class Day5 implements Day {

    /**
     * [-] [-] [C] [B] [H]
     * [W]     [D] [J] [Q] [B]
     * [P] [F] [Z] [F] [B] [L]
     * [G] [Z] [N] [P] [J] [S] [V]
     * [Z] [C] [H] [Z] [G] [T] [Z]     [C]
     * [V] [B] [M] [M] [C] [Q] [C] [G] [H]
     * [S] [V] [L] [D] [F] [F] [G] [L] [F]
     * [B] [J] [V] [L] [V] [G] [L] [N] [J]
     * 1   2   3   4   5   6   7   8   9
     */
    private final String[][] puzzleStacks = {
            {"B", "S", "V", "Z", "G", "P", "W"},
            {"J", "V", "B", "C", "Z", "F"},
            {"V", "L", "M", "H", "N", "Z", "D", "C"},
            {"L", "D", "M", "Z", "P", "F", "J", "B"},
            {"V", "F", "C", "G", "J", "B", "Q", "H"},
            {"G", "F", "Q", "T", "S", "L", "B"},
            {"L", "G", "C", "Z", "V"},
            {"N", "L", "G"},
            {"J", "F", "H", "C"}
    };

    /**
     * [-] [D]
     * [N] [C]
     * [Z] [M] [P]
     * 1   2   3
     */
    private final String[][] testStacks = {
            {"Z", "N"},
            {"M", "C", "D"},
            {"P"}
    };

    private String[][] startingStacks = puzzleStacks;

    private final Pattern p = Pattern.compile("\\d+");

    private Deque<String>[] stacks;

    private List<Operation> operations;

    private final String filename;
    private List<String> input;

    public Day5(String filename, boolean test) throws IOException {
        this.filename = filename;
        loadData();
        if (test) {
            startingStacks = testStacks;
        }
    }

    private void loadData() throws IOException {
        input = readInput(filename);
        prepareData();
    }

    private void prepareData() {
        operations = input.stream().map(this::resolveOperation).toList();
    }

    private Operation resolveOperation(String operation) {
        Matcher m = p.matcher(operation);
        m.find();
        int quantity = Integer.parseInt(m.group(0));
        m.find();
        int from = Integer.parseInt(m.group(0)) - 1;
        m.find();
        int to = Integer.parseInt(m.group(0)) - 1;
        return new Operation(quantity, from, to);
    }

    @Override
    public String calculateFirstStar() {
        resetStacks();
        for (Operation operation : operations) {
            for (int i = 0; i < operation.quantity; i++) {
                stacks[operation.to].add(stacks[operation.from].removeLast());
            }
        }
        return stream(stacks).sequential().map(Deque::peekLast).collect(joining());
    }

    @Override
    public String calculateSecondStar() {
        resetStacks();
        for (Operation operation : operations) {
            Deque<String> tempDeque = new ArrayDeque<>();
            for (int i = 0; i < operation.quantity; i++) {
                tempDeque.add(stacks[operation.from].removeLast());
            }
            for (int i = 0; i < operation.quantity; i++) {
                stacks[operation.to].add(tempDeque.removeLast());
            }
        }
        return stream(stacks).sequential().map(Deque::peekLast).collect(joining());
    }

    private void resetStacks() {
        stacks = new ArrayDeque[startingStacks.length];
        for (int i = 0; i < startingStacks.length; i++) {
            stacks[i] = new ArrayDeque<>(asList(startingStacks[i]));
        }
    }

    record Operation(int quantity, int from, int to) {
    }
}
