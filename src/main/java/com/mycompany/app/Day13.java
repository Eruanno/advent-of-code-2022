package com.mycompany.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.mycompany.app.FileReader.readInput;

public class Day13 implements Day {

    private final String filename;
    private List<String> input;

    public Day13(String filename) {
        this.filename = filename;
    }

    @Override
    public void loadData() throws IOException {
        input = readInput(filename);
    }

    @Override
    public String calculateFirstStar() {
        String left = "";
        String right = "";
        long sumOfIndicesInRightOrder = 0;
        int packetIndex = 1;
        for (String line : input) {
            if (left.isEmpty()) {
                left = line;
            } else if (right.isEmpty()) {
                right = line;
            } else if (line.isEmpty()) {
                if (compare(left, right) == 1) {
                    sumOfIndicesInRightOrder += packetIndex;
                }
                packetIndex++;
                left = "";
                right = "";
            }
        }
        return "" + sumOfIndicesInRightOrder;
    }

    @Override
    public String calculateSecondStar() {
        input.add("[[2]]");
        input.add("[[6]]");
        List<String> sortedInput = input.stream()
                                        .filter(p -> !p.isBlank())
                                        .sorted(this::reverse)
                                        .toList();
        return "" + (sortedInput.indexOf("[[2]]") + 1L) * (sortedInput.indexOf("[[6]]") + 1L);
    }

    private int reverse(String left, String right) {
        return compare(right, left);
    }

    private int compare(String leftPacket, String rightPacket) {
        String[] leftValues = splitByCommas(stripFromBrackets(leftPacket));
        String[] rightValues = splitByCommas(stripFromBrackets(rightPacket));
        for (int i = 0; i < leftValues.length; i++) {
            if (i == rightValues.length) {
                return -1;
            } else if (!isList(leftValues[i]) && !isList(rightValues[i])) {
                if (leftValues[i].isEmpty() || rightValues[i].isEmpty()) {
                    return -1;
                }
                int leftValue = Integer.parseInt(leftValues[i]);
                int rightValue = Integer.parseInt(rightValues[i]);
                if (leftValue < rightValue) {
                    return 1;
                } else if (leftValue > rightValue) {
                    return -1;
                }
            } else if (isList(leftValues[i]) && isList(rightValues[i])) {
                int order = compare(leftValues[i], rightValues[i]);
                if (order != 0) {
                    return order;
                }
            } else {
                int order = 0;
                if (isList(leftValues[i])) {
                    order = compare(leftValues[i], "[" + rightValues[i] + "]");
                } else if (isList(rightValues[i])) {
                    order = compare("[" + leftValues[i] + "]", rightValues[i]);
                }
                if (order != 0) {
                    return order;
                }
            }
        }
        if (leftValues.length < rightValues.length) {
            return 1;
        }
        return 0;
    }

    private String[] splitByCommas(String line) {
        List<String> values = new ArrayList<>();
        StringBuilder value = new StringBuilder();
        int openedBrackets = 0;
        for (char character : line.toCharArray()) {
            if (character == '[') {
                openedBrackets++;
            } else if (character == ']') {
                openedBrackets--;
            }
            if (character == ',' && openedBrackets == 0) {
                values.add(value.toString());
                value = new StringBuilder();
            } else {
                value.append(character);
            }
        }
        if (!value.toString().isBlank()) {
            values.add(value.toString());
        }
        return values.toArray(new String[0]);
    }

    private String stripFromBrackets(String line) {
        String result = line;
        if (canBeStripped(line)) {
            if (result.startsWith("[")) {
                result = result.substring(1);
            }
            if (result.endsWith("]")) {
                result = result.substring(0, result.length() - 1);
            }
        }
        return result;
    }

    private boolean canBeStripped(String line) {
        int openedBrackets = 0;
        for (char character : line.toCharArray()) {
            if (openedBrackets == 0 && character == ',') {
                return false;
            }
            if (character == '[') {
                openedBrackets++;
            }
            if (character == ']') {
                openedBrackets--;
            }
        }
        return true;
    }

    private boolean isList(String value) {
        return value.startsWith("[") && value.endsWith("]");
    }
}
