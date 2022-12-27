package com.mycompany.app;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import static com.mycompany.app.FileReader.readInput;
import static java.lang.Math.pow;
import static java.math.BigInteger.*;

public class Day25 implements Day {

    private final String filename;
    private List<String> input;

    public Day25(String filename) throws IOException {
        this.filename = filename;
        loadData();
    }

    private void loadData() throws IOException {
        input = readInput(filename);
        prepareData();
    }

    private void prepareData() {

    }

    @Override
    public String calculateFirstStar() {
        BigInteger sum = ZERO;
        for (String line : input) {
            sum = sum.add(snafuToDecimal(line));
        }
        return sum.toString();
    }

    @Override
    public String calculateSecondStar() {
        return null;
    }

    String decimalToSnafu(BigInteger number) {
        //String input = number.toString();
        StringBuilder result = new StringBuilder();
        /*for (int i = 0; i < input.length(); i++) {
            String digit = String.valueOf(input.charAt(input.length() - 1 - i));
            result.append(translateDigit(new BigInteger(digit)));
        }*/
        int mag = 0;
        double num = 0;
        while (num < number.longValue()) {
            mag++;
            num = pow(5, mag);
        }
        return result.reverse().toString();
    }

    private String translateDigit(BigInteger digit) {
        if (digit.equals(valueOf(-2))) {
            return "=";
        } else if (digit.equals(valueOf(-1))) {
            return "-";
        } else if (digit.equals(ZERO)) {
            return "0";
        } else if (digit.equals(ONE)) {
            return "1";
        } else if (digit.equals(TWO)) {
            return "2";
        } /*else if (digit.equals(valueOf(3))) {
            return "1=";
        } else if (digit.equals(valueOf(4))) {
            return "1-";
        }*/
        throw new NumberFormatException("");
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
            return valueOf(-2);
        } else if (digit == '-') {
            return valueOf(-1);
        } else if (digit == '0') {
            return ZERO;
        } else if (digit == '1') {
            return ONE;
        } else {
            return TWO;
        }
    }
}
