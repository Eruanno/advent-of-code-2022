package com.mycompany.app;

import java.io.IOException;
import java.util.List;

import static com.mycompany.app.FileReader.readInput;
import static com.mycompany.app.Logger.log;

public class Day17 implements Day {

    private char[][] map = new char[7][1000];
    private int highestFloor = 0;
    private int shape = 0;
    private String[] shapes = {"####", """
            .#.
            ###
            .#.""", """
            ..#
            ..#
            ###""", """
            #
            #
            #
            #""", """
            ##
            ##"""};

    public void solve() throws IOException {
        List<String> input = readInput("day-17-test");
        prepareData(input);
        log("Day 17:");
        log("First star:");
        log(calculateFirstStar());
        log("Second star:");
        log(calculateSecondStar());
    }

    private void prepareData(List<String> input) {

    }

    private Long calculateFirstStar() {
        int numberOfRocks = 2022;
        int width = 7;
        String shape = getNextShape();
        int shapeX = 2;
        int shapeY = highestFloor + 3;
        boolean shapeIsMoving = true;
        for (int i = 0; i < 12; i++) {
            if (!shapeIsMoving) {
                shape = getNextShape();
                shapeX = 2;
                shapeY = highestFloor + 3;
                char[][] s = transformToMatrix(shape);
            }
            if (shapeCanMove()) {
                moveShape();
            } else {
                shapeIsMoving = false;
            }
        }
        return -1L;
    }

    private char[][] transformToMatrix(String shape) {
        String[] shapeLines = shape.split("\n");
        char[][] result = new char[shapeLines.length][];
        for (int i = 0; i < result.length; i++) {
            result[i] = new char[shapeLines[i].length()];
            for (int j = 0; j < shapeLines[i].length(); j++) {
                if (shapeLines[i].charAt(j) == '.') {
                    result[i][j] = '.';
                }
                if (shapeLines[i].charAt(j) == '#') {
                    result[i][j] = '@';
                }
            }
        }
        return result;
    }

    private void moveShape() {

    }

    private boolean shapeCanMove() {
        return true;
    }

    private String getNextShape() {
        String nextShape = shapes[shape];
        shape = (shape + 1) % shapes.length;
        return nextShape;
    }

    private Long calculateSecondStar() {
        return -1L;
    }
}
