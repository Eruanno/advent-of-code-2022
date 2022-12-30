package com.mycompany.app;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import static com.mycompany.app.FileReader.readInput;
import static java.math.BigInteger.*;

public class Day25 implements Day {

    private final BigInteger MINUS_TWO = valueOf(-2);
    private final BigInteger MINUS_ONE = valueOf(-1);
    private final BigInteger THREE = valueOf(3);
    private final BigInteger FOUR = valueOf(4);
    private final BigInteger FIVE = valueOf(5);

    private final String filename;
    private List<String> input;

    public Day25(String filename) throws IOException {
        this.filename = filename;
        loadData();
    }

    private void loadData() throws IOException {
        input = readInput(filename);
    }

    @Override
    public String calculateFirstStar() {
        return decimalToSnafu(input.stream().map(this::snafuToDecimal).reduce(BigInteger::add).get());
    }

    @Override
    public String calculateSecondStar() {
        return null;
    }

    BigInteger snafuToDecimal(String number) {
        BigInteger result = ZERO;
        for (int i = 0; i < number.length(); i++) {
            char digit = number.charAt(number.length() - 1 - i);
            result = translateDigit(digit).multiply(valueOf(5).pow(i)).add(result);
        }
        return result;
    }

    private BigInteger translateDigit(char digit) {
        if (digit == '=') {
            return MINUS_TWO;
        } else if (digit == '-') {
            return MINUS_ONE;
        } else if (digit == '0') {
            return ZERO;
        } else if (digit == '1') {
            return ONE;
        } else if (digit == '2') {
            return TWO;
        }
        throw new NumberFormatException("");
    }

    String decimalToSnafu(BigInteger number) {
        StringBuilder result = new StringBuilder();
        while (!number.equals(ZERO)) {
            number = number.add(TWO);
            result.append(translateDigit(number.remainder(FIVE)));
            number = number.divide(FIVE);
        }
        return result.reverse().toString();
    }

    private String translateDigit(BigInteger digit) {
        if (digit.equals(ZERO)) {
            return "=";
        } else if (digit.equals(ONE)) {
            return "-";
        } else if (digit.equals(TWO)) {
            return "0";
        } else if (digit.equals(THREE)) {
            return "1";
        } else if (digit.equals(FOUR)) {
            return "2";
        }
        throw new NumberFormatException("");
    }
}
