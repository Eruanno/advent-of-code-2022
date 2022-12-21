package com.mycompany.app;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mycompany.app.FileReader.readInput;
import static com.mycompany.app.Logger.log;

public class Day21 implements Day {

    Map<String, Monkey> monkeys = new HashMap<>();

    private final String filename;
    private List<String> input;

    public Day21(String filename) {
        this.filename = filename;
    }

    @Override
    public void loadData() throws IOException {
        input = readInput(filename);
        prepareData();
    }

    private void prepareData() {
        for (String line : input) {
            String[] parts = line.split(" ");
            String name = parts[0].substring(0, 4);
            if (parts.length == 2) {
                monkeys.put(name, new Monkey(name, new BigDecimal(parts[1]), null, null, null));
            }
            if (parts.length == 4) {
                monkeys.put(name, new Monkey(name, null, parts[2], parts[1], parts[3]));
            }
        }
    }

    @Override
    public String calculateFirstStar() {
        return "" + returnValue("root");
    }

    private long returnValue(String name) {
        Monkey monkey = monkeys.get(name);
        if (monkey.value != null) {
            return monkey.value.longValue();
        }
        long result;
        switch (monkey.operation) {
            case "+" -> result = returnValue(monkey.nameA) + returnValue(monkey.nameB);
            case "-" -> result = returnValue(monkey.nameA) - returnValue(monkey.nameB);
            case "*" -> result = returnValue(monkey.nameA) * returnValue(monkey.nameB);
            case "/" -> result = returnValue(monkey.nameA) / returnValue(monkey.nameB);
            case "=" -> result = returnValue(monkey.nameA) == returnValue(monkey.nameB) ? 1 : 0;
            default -> throw new IllegalStateException("Unexpected value: " + monkey.operation);
        }
        return result;
    }

    @Override
    public String calculateSecondStar() {
        Monkey root = monkeys.remove("root");
        monkeys.put("root", new Monkey("root", null, "=", root.nameA, root.nameB));
        preCalcValues("root");
        monkeys.remove("humn");
        monkeys.put("humn", new Monkey("humn", null, null, null, null));
        BigDecimal number;
        root = monkeys.get("root");
        Monkey a = monkeys.get(root.nameA);
        Monkey b = monkeys.get(root.nameB);
        if (a.value != null) {
            number = returnValueReversed(a.value, b.name);
        } else {
            number = returnValueReversed(b.value, a.name);
        }
        return number.toString();
    }

    private Result preCalcValues(String name) {
        Monkey monkey = monkeys.get(name);
        if (monkey.value != null) {
            return new Result(monkey.value, monkey.name.equals("humn"));
        }
        BigDecimal result;
        Result resultA = preCalcValues(monkey.nameA);
        Result resultB = preCalcValues(monkey.nameB);
        switch (monkey.operation) {
            case "+" -> result = resultA.value.add(resultB.value);
            case "-" -> result = resultA.value.subtract(resultB.value, MathContext.DECIMAL64);
            case "*" -> result = resultA.value.multiply(resultB.value);
            case "/" -> result = resultA.value.divide(resultB.value);
            case "=" -> result = resultA.value.equals(resultB.value) ? BigDecimal.ONE : BigDecimal.ZERO;
            default -> throw new IllegalStateException("Unexpected value: " + monkey.operation);
        }
        boolean humanBranch = resultA.humanBranch || resultB.humanBranch;
        if (!humanBranch) {
            monkeys.remove(name);
            monkeys.remove(monkey.nameA);
            monkeys.remove(monkey.nameB);
            monkeys.put(name, new Monkey(name, result, null, null, null));
        }
        return new Result(result, humanBranch);
    }

    private BigDecimal returnValueReversed(BigDecimal value, String rootName) {
        if (rootName.equals("humn")) {
            return value;
        }
        Monkey monkey = monkeys.get(rootName);
        if (monkey.value != null) {
            return monkey.value;
        }
        BigDecimal a = monkeys.get(monkey.nameA).value;
        if (a != null) {
            return returnValueReversed(reverseCalculation(monkey.operation, a, value), monkey.nameB);
        }
        BigDecimal b = monkeys.get(monkey.nameB).value;
        return returnValueReversed(reverseCalculation(monkey.operation, value, b), monkey.nameA);
    }

    private BigDecimal reverseCalculation(String operation, BigDecimal a, BigDecimal b) {
        BigDecimal result;
        log(b.toString() + " " + o(operation) + " " + a.toString());
        switch (operation) {
            case "+" -> result = b.subtract(a, MathContext.DECIMAL64);
            case "-" -> result = b.add(a);
            case "*" -> result = b.divide(a);
            case "/" -> result = b.multiply(a);
            default -> throw new IllegalStateException("Unexpected value: " + operation);
        }
        return result;
    }

    private String o(String operation) {
        if ("+".equals(operation)) {
            return "-";
        }
        if ("-".equals(operation)) {
            return "+";
        }
        if ("/".equals(operation)) {
            return "*";
        }
        return "/";
    }

    private record Monkey(String name, BigDecimal value, String operation, String nameA, String nameB) {
    }

    private record Result(BigDecimal value, boolean humanBranch) {
    }
}
