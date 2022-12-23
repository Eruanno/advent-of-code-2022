package com.mycompany.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.mycompany.app.FileReader.readInput;
import static java.lang.Math.max;

public class Day22 implements Day {

    private final List<Move> path = new ArrayList<>();
    private char[][] map;
    private int height = 0;
    private int width = 0;

    private final String filename;
    private List<String> input;

    public Day22(String filename) throws IOException {
        this.filename = filename;
        loadData();
    }

    private void loadData() throws IOException {
        input = readInput(filename);
        prepareData();
    }

    private void prepareData() {
        List<String> rows = new ArrayList<>();
        for (String line : input) {
            if (line.contains(".")) {
                width = max(width, line.length());
                rows.add(line);
            } else if (line.isEmpty()) {
                this.height = rows.size();
                map = new char[height][width];
                for (int h = 0; h < height; h++) {
                    String row = rows.get(h);
                    for (int w = 0; w < width; w++) {
                        if (w < row.length()) {
                            map[h][w] = row.charAt(w);
                        } else {
                            map[h][w] = ' ';
                        }
                    }
                }
            } else if (line.contains("R")) {
                char nextTurn = ' ';
                StringBuilder steps = new StringBuilder();
                for (int i = 0; i <= line.length(); i++) {
                    if (i == line.length()) {
                        path.add(new Move(nextTurn, Integer.parseInt(steps.toString())));
                        break;
                    }
                    char character = line.charAt(i);
                    if (character == 'R' || character == 'L') {
                        path.add(new Move(nextTurn, Integer.parseInt(steps.toString())));
                        steps = new StringBuilder();
                        nextTurn = character;
                    } else {
                        steps.append(character);
                    }
                }
            }
        }
    }

    @Override
    public String calculateFirstStar() {
        int column = input.get(0).indexOf('.');
        int row = 0;
        char facing = '>';
        System.out.println("start:\t\t\t\tcolumn: %d\trow: %d\tfacing: %c".formatted(column, row, facing));
        for (Move move : path) {
            if (move.turn == 'R') {
                if (facing == '>') {
                    facing = 'v';
                } else if (facing == 'v') {
                    facing = '<';
                } else if (facing == '<') {
                    facing = '^';
                } else {
                    facing = '>';
                }
            } else if (move.turn == 'L') {
                if (facing == '>') {
                    facing = '^';
                } else if (facing == 'v') {
                    facing = '>';
                } else if (facing == '<') {
                    facing = 'v';
                } else {
                    facing = '<';
                }
            }
            for (int i = 0; i < move.length; i++) {
                if (facing == '>') {
                    int nextColumn = nextColumn(column + 1, row, facing);
                    if (isWall(nextColumn, row)) {
                        break;
                    }
                    column = nextColumn;
                } else if (facing == 'v') {
                    int nextRow = nextRow(column, row + 1, facing);
                    if (isWall(column, nextRow)) {
                        break;
                    }
                    row = nextRow;
                } else if (facing == '<') {
                    int nextColumn = nextColumn(column - 1, row, facing);
                    if (isWall(nextColumn, row)) {
                        break;
                    }
                    column = nextColumn;
                } else {
                    int nextRow = nextRow(column, row - 1, facing);
                    if (isWall(column, nextRow)) {
                        break;
                    }
                    row = nextRow;
                }
            }
            System.out.println("turn: %c\tlength: %d\tcolumn: %d\trow: %d\tfacing: %c".formatted(move.turn, move.length, column, row, facing));
        }
        return "" + (1000 * (row + 1) + 4 * (column + 1) + facingValue(facing));
    }

    private int nextColumn(int column, int row, char facing) {
        if (column < 0 || (column < width && map[row][column] == ' ' && facing == '<')) {
            for (int c = width - 1; c >= 0; c--) {
                if (map[row][c] != ' ') {
                    return c;
                }
            }
        }
        if (column >= width || (map[row][column] == ' ' && facing == '>')) {
            for (int c = 0; c < width; c++) {
                if (map[row][c] != ' ') {
                    return c;
                }
            }
        }
        return column;
    }


    private int nextRow(int column, int row, char facing) {
        if (row < 0 || (row < height && map[row][column] == ' ' && facing == '^')) {
            for (int r = height - 1; r >= 0; r--) {
                if (map[r][column] != ' ') {
                    return r;
                }
            }
        }
        if (row >= height || (map[row][column] == ' ' && facing == 'v')) {
            for (int r = 0; r < height; r++) {
                if (map[r][column] != ' ') {
                    return r;
                }
            }
        }
        return row;
    }

    private boolean isWall(int column, int row) {
        return map[row][column] == '#';
    }

    private int facingValue(char facing) {
        if (facing == '>') {
            return 0;
        } else if (facing == 'v') {
            return 1;
        } else if (facing == '<') {
            return 2;
        }
        return 3;
    }

    @Override
    public String calculateSecondStar() {
        return null;
    }

    private record Move(char turn, int length) {
    }
}
