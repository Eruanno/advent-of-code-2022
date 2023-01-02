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
    private boolean test;

    public Day22(String filename, boolean test) throws IOException {
        this.filename = filename;
        this.test = test;
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
        char direction = '>';
        for (Move move : path) {
            direction = turn(direction, move.turn);
            for (int i = 0; i < move.length; i++) {
                Position nextPosition;
                if (direction == '>') {
                    nextPosition = nextColumn(column + 1, row, direction);
                } else if (direction == 'v') {
                    nextPosition = nextRow(column, row + 1, direction);
                } else if (direction == '<') {
                    nextPosition = nextColumn(column - 1, row, direction);
                } else {
                    nextPosition = nextRow(column, row - 1, direction);
                }
                if (isWall(nextPosition)) {
                    break;
                }
                row = nextPosition.row;
                column = nextPosition.column;
                direction = nextPosition.direction;
            }
        }
        return "" + (1000 * (row + 1) + 4 * (column + 1) + directionPoints(direction));
    }

    private char turn(char direction, char turn) {
        if (turn == 'R') {
            if (direction == '>') {
                return 'v';
            } else if (direction == 'v') {
                return '<';
            } else if (direction == '<') {
                return '^';
            } else {
                return '>';
            }
        } else if (turn == 'L') {
            if (direction == '>') {
                return '^';
            } else if (direction == 'v') {
                return '>';
            } else if (direction == '<') {
                return 'v';
            } else {
                return '<';
            }
        }
        return direction;
    }

    private Position nextColumn(int column, int row, char direction) {
        if (column < 0 || (column < width && map[row][column] == ' ' && direction == '<')) {
            for (int c = width - 1; c >= 0; c--) {
                if (map[row][c] != ' ') {
                    return new Position(row, c, direction);
                }
            }
        }
        if (column >= width || (map[row][column] == ' ' && direction == '>')) {
            for (int c = 0; c < width; c++) {
                if (map[row][c] != ' ') {
                    return new Position(row, c, direction);
                }
            }
        }
        return new Position(row, column, direction);
    }

    private Position nextRow(int column, int row, char direction) {
        if (row < 0 || (row < height && map[row][column] == ' ' && direction == '^')) {
            for (int r = height - 1; r >= 0; r--) {
                if (map[r][column] != ' ') {
                    return new Position(r, column, direction);
                }
            }
        }
        if (row >= height || (map[row][column] == ' ' && direction == 'v')) {
            for (int r = 0; r < height; r++) {
                if (map[r][column] != ' ') {
                    return new Position(r, column, direction);
                }
            }
        }
        return new Position(row, column, direction);
    }

    private boolean isWall(Position position) {
        return map[position.row][position.column] == '#';
    }

    private int directionPoints(char facing) {
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
        int column = input.get(0).indexOf('.');
        int row = 0;
        char direction = '>';
        for (Move move : path) {
            direction = turn(direction, move.turn);
            for (int i = 0; i < move.length; i++) {
                Position nextPosition = test ? nextPositionTest(column, row, direction) : nextPosition(column, row, direction);
                if (isWall(nextPosition)) {
                    break;
                }
                row = nextPosition.row;
                column = nextPosition.column;
                direction = nextPosition.direction;
            }
        }
        return "" + (1000 * (row + 1) + 4 * (column + 1) + directionPoints(direction));
    }

    private Position nextPositionTest(int column, int row, char direction) {
        if (direction == '>') {
            if (column == width - 1 || (map[row][column + 1] == ' ')) {
                if (row == 0) {
                    row = 11;
                    column = 15;
                    direction = '<';
                } else if (row == 1) {
                    row = 10;
                    column = 15;
                    direction = '<';
                } else if (row == 2) {
                    row = 9;
                    column = 15;
                    direction = '<';
                } else if (row == 3) {
                    row = 8;
                    column = 15;
                    direction = '<';
                } else if (row == 4) {
                    row = 8;
                    column = 15;
                    direction = 'v';
                } else if (row == 5) {
                    row = 8;
                    column = 14;
                    direction = 'v';
                } else if (row == 6) {
                    row = 8;
                    column = 13;
                    direction = 'v';
                } else if (row == 7) {
                    row = 8;
                    column = 12;
                    direction = 'v';
                } else if (row == 8) {
                    row = 3;
                    column = 11;
                    direction = '<';
                } else if (row == 9) {
                    row = 2;
                    column = 11;
                    direction = '<';
                } else if (row == 10) {
                    row = 1;
                    column = 11;
                    direction = '<';
                } else if (row == 11) {
                    row = 0;
                    column = 11;
                    direction = '<';
                }
            } else {
                return new Position(row, column + 1, direction);
            }
        } else if (direction == '<') {
            if (column == 0 || map[row][column - 1] == ' ') {
                if (row == 0) {
                    row = 4;
                    column = 4;
                    direction = 'v';
                } else if (row == 1) {
                    row = 4;
                    column = 5;
                    direction = 'v';
                } else if (row == 2) {
                    row = 4;
                    column = 6;
                    direction = 'v';
                } else if (row == 3) {
                    row = 4;
                    column = 7;
                    direction = 'v';
                } else if (row == 4) {
                    row = 11;
                    column = 15;
                    direction = '^';
                } else if (row == 5) {
                    row = 11;
                    column = 14;
                    direction = '^';
                } else if (row == 6) {
                    row = 11;
                    column = 13;
                    direction = '^';
                } else if (row == 7) {
                    row = 11;
                    column = 12;
                    direction = '^';
                } else if (row == 8) {
                    row = 7;
                    column = 7;
                    direction = '^';
                } else if (row == 9) {
                    row = 7;
                    column = 6;
                    direction = '^';
                } else if (row == 10) {
                    row = 7;
                    column = 5;
                    direction = '^';
                } else if (row == 11) {
                    row = 7;
                    column = 4;
                    direction = '^';
                }
            } else {
                return new Position(row, column - 1, direction);
            }
        } else if (direction == 'v') {
            if (row == height - 1 || map[row + 1][column] == ' ') {
                if (column == 0) {
                    row = 11;
                    column = 11;
                    direction = '^';
                } else if (column == 1) {
                    row = 11;
                    column = 10;
                    direction = '^';
                } else if (column == 2) {
                    row = 11;
                    column = 9;
                    direction = '^';
                } else if (column == 3) {
                    row = 11;
                    column = 8;
                    direction = '^';
                } else if (column == 4) {
                    row = 11;
                    column = 8;
                    direction = '>';
                } else if (column == 5) {
                    row = 10;
                    column = 8;
                    direction = '>';
                } else if (column == 6) {
                    row = 9;
                    column = 8;
                    direction = '>';
                } else if (column == 7) {
                    row = 8;
                    column = 8;
                    direction = '>';
                } else if (column == 8) {
                    row = 7;
                    column = 3;
                    direction = '^';
                } else if (column == 9) {
                    row = 7;
                    column = 2;
                    direction = '^';
                } else if (column == 10) {
                    row = 7;
                    column = 1;
                    direction = '^';
                } else if (column == 11) {
                    row = 7;
                    column = 0;
                    direction = '^';
                } else if (column == 12) {
                    row = 4;
                    column = 0;
                    direction = '>';
                } else if (column == 13) {
                    row = 5;
                    column = 0;
                    direction = '>';
                } else if (column == 14) {
                    row = 6;
                    column = 0;
                    direction = '>';
                } else if (column == 15) {
                    row = 7;
                    column = 0;
                    direction = '>';
                }
            } else {
                return new Position(row + 1, column, direction);
            }
        } else if (direction == '^') {
            if (row == 0 || map[row - 1][column] == ' ') {
                if (column == 0) {
                    row = 0;
                    column = 11;
                    direction = 'v';
                } else if (column == 1) {
                    row = 0;
                    column = 10;
                    direction = 'v';
                } else if (column == 2) {
                    row = 0;
                    column = 9;
                    direction = 'v';
                } else if (column == 3) {
                    row = 0;
                    column = 8;
                    direction = 'v';
                } else if (column == 4) {
                    row = 0;
                    column = 8;
                    direction = '>';
                } else if (column == 5) {
                    row = 1;
                    column = 8;
                    direction = '>';
                } else if (column == 6) {
                    row = 2;
                    column = 8;
                    direction = '>';
                } else if (column == 7) {
                    row = 3;
                    column = 8;
                    direction = '>';
                } else if (column == 8) {
                    row = 4;
                    column = 3;
                    direction = 'v';
                } else if (column == 9) {
                    row = 4;
                    column = 2;
                    direction = 'v';
                } else if (column == 10) {
                    row = 4;
                    column = 1;
                    direction = 'v';
                } else if (column == 11) {
                    row = 4;
                    column = 0;
                    direction = 'v';
                } else if (column == 12) {
                    row = 7;
                    column = 11;
                    direction = '<';
                } else if (column == 13) {
                    row = 6;
                    column = 11;
                    direction = '<';
                } else if (column == 14) {
                    row = 5;
                    column = 11;
                    direction = '<';
                } else if (column == 15) {
                    row = 4;
                    column = 11;
                    direction = '<';
                }
            } else {
                return new Position(row - 1, column, direction);
            }
        }
        return new Position(row, column, direction);
    }

    private Position nextPosition(int column, int row, char direction) {
        int nextRow = -1;
        int nextColumn = -1;
        char nextDirection = '.';
        if (direction == '>') {
            if (column == width - 1 || (map[row][column + 1] == ' ')) {
                if (row >= 0 && row < 50) { // B -> E
                    nextRow = 149 - row;
                    nextColumn = 99;
                    nextDirection = '<';
                } else if (row >= 50 && row < 100) { // C -> B
                    nextRow = 49;
                    nextColumn = row + 50;
                    nextDirection = '^';
                } else if (row >= 100 && row < 150) { // E -> B
                    nextRow = 149 - row;
                    nextColumn = 149;
                    nextDirection = '<';
                } else if (row >= 150 && row < 200) { // F -> E
                    nextRow = 149;
                    nextColumn = row - 100;
                    nextDirection = '^';
                }
            } else {
                return new Position(row, column + 1, direction);
            }
        } else if (direction == '<') {
            if (column == 0 || map[row][column - 1] == ' ') {
                if (row >= 0 && row < 50) { // A -> D
                    nextRow = 149 - row;
                    nextColumn = 0;
                    nextDirection = '>';
                } else if (row >= 50 && row < 100) { // C -> D
                    nextColumn = row - 50;
                    nextRow = 100;
                    nextDirection = 'v';
                } else if (row >= 100 && row < 150) { // D -> A
                    nextRow = 149 - row;
                    nextColumn = 50;
                    nextDirection = '>';
                } else if (row >= 150 && row < 200) { // F -> A
                    nextColumn = row - 100;
                    nextRow = 0;
                    nextDirection = 'v';
                }
            } else {
                return new Position(row, column - 1, direction);
            }
        } else if (direction == 'v') {
            if (row == height - 1 || map[row + 1][column] == ' ') {
                if (column >= 0 && column < 50) { // F -> B
                    nextRow = 0;
                    nextColumn = column + 100;
                    nextDirection = 'v';
                } else if (column >= 50 && column < 100) { // E -> F
                    nextRow = column + 100;
                    nextColumn = 49;
                    nextDirection = '<';
                } else if (column >= 100 && column < 150) { // B -> C
                    nextRow = column - 50;
                    nextColumn = 99;
                    nextDirection = '<';
                }
            } else {
                return new Position(row + 1, column, direction);
            }
        } else if (direction == '^') {
            if (row == 0 || map[row - 1][column] == ' ') {
                if (column >= 0 && column < 50) { // D -> C
                    nextRow = 50 + column;
                    nextColumn = 50;
                    nextDirection = '>';
                } else if (column >= 50 && column < 100) { // A -> F
                    nextRow = column + 100;
                    nextColumn = 0;
                    nextDirection = '>';
                } else if (column >= 100 && column < 150) { // B -> F
                    nextRow = 199;
                    nextColumn = column - 100;
                    nextDirection = '^';
                }
            } else {
                return new Position(row - 1, column, direction);
            }
        }
        return new Position(nextRow, nextColumn, nextDirection);
    }

    private record Position(int row, int column, char direction) {
    }

    private record Move(char turn, int length) {
    }
}
