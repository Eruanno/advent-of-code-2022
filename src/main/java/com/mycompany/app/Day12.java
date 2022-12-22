package com.mycompany.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.mycompany.app.FileReader.readInput;
import static java.util.Comparator.comparingInt;

public class Day12 implements Day {

    private final List<Point> allStartingPoints = new ArrayList<>();
    private Point startingPoint;
    private Point destinationPoint;
    private char[][] map;
    private long[][] steps;
    private int height = 0;
    private int width = 0;

    private final String filename;
    private List<String> input;

    public Day12(String filename) throws IOException {
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
        map = new char[height][];
        steps = new long[height][];
        for (int i = 0; i < height; i++) {
            map[i] = new char[width];
            steps[i] = new long[width];
            String line = input.get(i);
            for (int j = 0; j < line.length(); j++) {
                map[i][j] = line.charAt(j);
                steps[i][j] = Integer.MAX_VALUE;
                if (map[i][j] == 'S') {
                    map[i][j] = 'a';
                    startingPoint = new Point(i, j, null, 0);
                }
                if (map[i][j] == 'E') {
                    map[i][j] = 'z';
                    destinationPoint = new Point(i, j, null, 0);
                }
                if (map[i][j] == 'a') {
                    allStartingPoints.add(new Point(i, j, null, 0));
                }
            }
        }
    }

    @Override
    public String calculateFirstStar() {
        return "" + findNumberOfStepsFrom(startingPoint);
    }

    @Override
    public String calculateSecondStar() {
        return "" + allStartingPoints.stream().map(this::findNumberOfStepsFrom).min(Long::compareTo).orElse(-1L);
    }

    private long findNumberOfStepsFrom(Point point) {
        List<Point> unvisitedPoints = new ArrayList<>();
        unvisitedPoints.add(point);
        while (!unvisitedPoints.isEmpty()) {
            unvisitedPoints.sort(comparingInt(o -> o.step));
            visitPoint(unvisitedPoints, unvisitedPoints.remove(0));
        }
        return steps[destinationPoint.x][destinationPoint.y];
    }

    private void visitPoint(List<Point> unvisitedPoints, Point toVisit) {
        int x = toVisit.x;
        int y = toVisit.y;
        if (x < 0 || y < 0 || x == height || y == width) {
            return;
        }
        if (steps[x][y] > toVisit.step) {
            steps[x][y] = toVisit.step;
            if (isInMap(x + 1, y) && isClimbable(x + 1, y, x, y) && isNotPrevious(toVisit.prev, x + 1, y)) {
                unvisitedPoints.add(new Point(x + 1, y, toVisit, toVisit.step + 1));
            }
            if (isInMap(x - 1, y) && isClimbable(x - 1, y, x, y) && isNotPrevious(toVisit.prev, x - 1, y)) {
                unvisitedPoints.add(new Point(x - 1, y, toVisit, toVisit.step + 1));
            }
            if (isInMap(x, y + 1) && isClimbable(x, y + 1, x, y) && isNotPrevious(toVisit.prev, x, y + 1)) {
                unvisitedPoints.add(new Point(x, y + 1, toVisit, toVisit.step + 1));
            }
            if (isInMap(x, y - 1) && isClimbable(x, y - 1, x, y) && isNotPrevious(toVisit.prev, x, y - 1)) {
                unvisitedPoints.add(new Point(x, y - 1, toVisit, toVisit.step + 1));
            }
        }
    }

    private boolean isInMap(int x, int y) {
        return x >= 0 && y >= 0 && x < height && y < width;
    }

    private boolean isClimbable(int aX, int aY, int bX, int bY) {
        int a = map[aX][aY];
        int b = map[bX][bY];
        return a - b < 2;
    }

    private boolean isNotPrevious(Point prev, int x, int y) {
        if (prev != null) {
            return !(prev.x == x + 1 && prev.y == y);
        }
        return true;
    }

    private record Point(int x, int y, Point prev, int step) {
    }
}
