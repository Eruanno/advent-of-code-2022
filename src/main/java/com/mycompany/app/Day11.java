package com.mycompany.app;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mycompany.app.FileReader.readInput;
import static java.util.Arrays.stream;

public class Day11 implements Day {
    private static final Pattern p = Pattern.compile("\\d+");
    private Monkey[] monkeys;

    private final String filename;
    private List<String> input;

    public Day11(String filename) {
        this.filename = filename;
    }

    @Override
    public void loadData() throws IOException {
        input = readInput(filename);
        prepareData();
    }

    private void prepareData() {
        int id = -1;
        ArrayDeque<Long> items = new ArrayDeque<>();
        Operation operation = new Operation('-', 1L);
        long divisor = 1L;
        int trueId = -1;
        int falseId = -1;
        List<Monkey> monkeyList = new ArrayList<>();
        for (String line : input) {
            if (line.startsWith("Monkey")) {
                id++;
            } else if (line.contains("Starting items:")) {
                Matcher m = p.matcher(line);
                items = new ArrayDeque<>();
                while (m.find()) {
                    items.add(Long.parseLong(m.group(0)));
                }
            } else if (line.contains("Operation:")) {
                Matcher m = p.matcher(line);
                long value = Long.MAX_VALUE;
                if (m.find()) {
                    value = Long.parseLong(m.group(0));
                }
                if (line.contains("*")) {
                    operation = new Operation('*', value);
                } else if (line.contains("+")) {
                    operation = new Operation('+', value);
                }
            } else if (line.contains("Test: divisible by")) {
                Matcher m = p.matcher(line);
                if (m.find()) {
                    divisor = Long.parseLong(m.group(0));
                }
            } else if (line.contains("If true:")) {
                Matcher m = p.matcher(line);
                if (m.find()) {
                    trueId = Integer.parseInt(m.group(0));
                }
            } else if (line.contains("If false:")) {
                Matcher m = p.matcher(line);
                if (m.find()) {
                    falseId = Integer.parseInt(m.group(0));
                }
            } else if (line.isEmpty()) {
                monkeyList.add(new Monkey(id, items, operation, divisor, trueId, falseId));
            }
        }
        monkeys = monkeyList.toArray(new Monkey[0]);
    }

    @Override
    public String calculateFirstStar() {
        return calc(20, true, 3);
    }

    @Override
    public String calculateSecondStar() {
        long divisor = stream(monkeys)
                .map(m -> m.divisor)
                .reduce(1L, (a, b) -> a * b);
        return calc(10000, false, divisor);
    }

    private String calc(int iterations, boolean division, long divisor) {
        for (int i = 0; i < iterations; i++) {
            for (Monkey monkey : monkeys) {
                while (!monkey.items.isEmpty()) {
                    long item = monkey.items.remove();
                    long v = monkey.operation.value == Long.MAX_VALUE ? item : monkey.operation.value;
                    if (monkey.operation.operation == '+') {
                        item += (v);
                    } else if (monkey.operation.operation == '*') {
                        item *= (v);
                    }
                    monkey.inspections = monkey.inspections + 1L;
                    if (division) {
                        item /= divisor;
                    } else {
                        item %= divisor;
                    }
                    if (item % monkey.divisor == 0) {
                        monkeys[monkey.trueId].items.add(item);
                    } else {
                        monkeys[monkey.falseId].items.add(item);
                    }
                }
            }
        }
        return "" + stream(monkeys)
                .map(m -> m.inspections)
                .sorted(Comparator.reverseOrder())
                .limit(2)
                .reduce(1L, (a, b) -> a * b);
    }

    private static class Monkey {
        private final int id;
        private final Deque<Long> items;
        private final Operation operation;
        private final Long divisor;
        private final int trueId;
        private final int falseId;
        private Long inspections = 0L;

        Monkey(int id, Deque<Long> items, Operation operation, Long divisor, int trueId, int falseId) {
            this.id = id;
            this.items = items;
            this.operation = operation;
            this.divisor = divisor;
            this.trueId = trueId;
            this.falseId = falseId;
        }
    }

    record Operation(char operation, Long value) {
    }
}