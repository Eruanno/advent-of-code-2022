package com.mycompany.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.mycompany.app.FileReader.readInput;

public class Day24 implements Day {

    private final String filename;
    private final List<Blizzard> blizzards = new ArrayList<>();
    private List<String> input;
    private int width = 0;
    private int height = 0;
    private int eX = 0;
    private int eY = 1;

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
        ;
        for (int y = 0; y < input.size(); y++) {
            String line = input.get(y);
            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);
                if (c == '>' || c == 'v' || c == '<' || c == '^') {
                    blizzards.add(new Blizzard(y, x, c));
                }
            }
        }
    }

    @Override
    public String calculateFirstStar() {
        displayMap();
        int step = 0;
        while (eX != width - 2 || eY != height - 1) {
            // move blizzards
            for (Blizzard b : blizzards) {
                b.move();
            }
            // move expedition
            moveExpedition();
            step++;
            System.out.println("After step #" + step + ":");
            displayMap();
        }
        return "" + step;
    }

    @Override
    public String calculateSecondStar() {
        return null;
    }

    private void moveExpedition() {
        if (isFieldFree(eX, eY + 1)) {
            eY++;
        } else if (isFieldFree(eX + 1, eY)) {
            eX++;
        } else if (isFieldFree(eX - 1, eY)) {
            eX--;
        } else if (isFieldFree(eX, eY - 1)) {
            eY--;
        }
    }

    private boolean isFieldFree(int column, int row) {
        if (column < 1 || row < 1 || column > width - 1 || row > height - 1) {
            return false;
        }
        for (Blizzard blizzard : blizzards) {
            if (blizzard.c == column && blizzard.r == row) {
                return false;
            }
        }
        return true;
    }

    private void displayMap() {
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                int row = r;
                int column = c;
                long blizzard = blizzards.stream().filter(b -> b.r == row && b.c == column).count();
                if (r == height - 1 && c == width - 2) {
                    System.out.print("E");
                } else if (r == eX && c == eY) {
                    System.out.print("@");
                } else if (c == 0 || r == 0 || c == width - 1 || r == height - 1) {
                    System.out.print("#");
                } else if (blizzard > 0) {
                    System.out.print(blizzard);
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }

    private class Blizzard {
        int r;//row
        int c;//column
        char d;//direction

        Blizzard(int row, int column, char direction) {
            this.r = row;
            this.c = column;
            this.d = direction;
        }

        void move() {
            if (d == '^') {
                if (r > 1) {
                    r--;
                } else {
                    r = height - 1;
                }
            }
            if (d == '>') {
                if (c < width - 1) {
                    c++;
                } else {
                    c = 1;
                }
            }
            if (d == 'v') {
                if (r < height - 2) {
                    r--;
                } else {
                    r = 1;
                }
            }
            if (d == '<') {
                if (c > 1) {
                    c--;
                } else {
                    c = width - 2;
                }
            }
        }
    }
}
