package com.mycompany.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.mycompany.app.FileReader.readInput;
import static com.mycompany.app.Logger.log;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

public class Day17 implements Day {

    private String jets;
    private int jet = 0;
    private List<IPoint> map = new ArrayList<>();
    private long highestFloor = 0;
    private int shape = 0;
    private List<List<IPoint>> shapes = new ArrayList<>();

    private final String filename;
    private List<String> input;

    public Day17(String filename) {
        this.filename = filename;
    }

    @Override
    public void loadData() throws IOException {
        input = readInput(filename);
        prepareData();
    }

    private void prepareData() {
        jets = input.get(0);

        List<IPoint> shapeA = new ArrayList<>();
        shapeA.add(new IPoint(0, 0));
        shapeA.add(new IPoint(1, 0));
        shapeA.add(new IPoint(2, 0));
        shapeA.add(new IPoint(3, 0));
        shapes.add(shapeA);

        List<IPoint> shapeB = new ArrayList<>();
        shapeB.add(new IPoint(1, 0));
        shapeB.add(new IPoint(0, 1));
        shapeB.add(new IPoint(1, 1));
        shapeB.add(new IPoint(2, 1));
        shapeB.add(new IPoint(1, 2));
        shapes.add(shapeB);

        List<IPoint> shapeC = new ArrayList<>();
        shapeC.add(new IPoint(0, 0));
        shapeC.add(new IPoint(1, 0));
        shapeC.add(new IPoint(2, 0));
        shapeC.add(new IPoint(2, 1));
        shapeC.add(new IPoint(2, 2));
        shapes.add(shapeC);

        List<IPoint> shapeD = new ArrayList<>();
        shapeD.add(new IPoint(0, 0));
        shapeD.add(new IPoint(0, 1));
        shapeD.add(new IPoint(0, 2));
        shapeD.add(new IPoint(0, 3));
        shapes.add(shapeD);

        List<IPoint> shapeE = new ArrayList<>();
        shapeE.add(new IPoint(0, 0));
        shapeE.add(new IPoint(0, 1));
        shapeE.add(new IPoint(1, 0));
        shapeE.add(new IPoint(1, 1));
        shapes.add(shapeE);
    }

    @Override
    public String calculateFirstStar() {
        return calculate(2022);
    }

    @Override
    public String calculateSecondStar() {
        return calculate(1_000_000_000_000L);
    }

    private String calculate(long numberOfRocks) {
        List<Point> shape = getNextShape();
        int shapeX = 2;
        int shapeY = (int) (highestFloor + 3);
        transform(shape, shapeX, shapeY);
        boolean moveDown = false;
        char move = getNextMove(moveDown);
        for (long i = 0; i < numberOfRocks; ) {
            if (!moveDown) {
                moveShape(shape, move);
                move = getNextMove(moveDown);
                moveDown = true;
            } else {
                if (shapeCanMoveDown(shape)) {
                    moveShapeDown(shape);
                } else {
                    map.addAll(shape.stream().map(p -> new IPoint(p.x, p.y)).toList());
                    highestFloor = map.stream().map(p -> p.y).max(Integer::compareTo).get();
                    while (map.size() > 100) {
                        map.remove(0);
                    }
                    findPattern();
                    shape = getNextShape();
                    shapeY = (int) (highestFloor + 4);
                    transform(shape, shapeX, shapeY);
                    i++;
                }
                moveDown = false;
            }
        }
        return "" + highestFloor;
    }

    private void findPattern() {
        Map<Integer, Set<IPoint>> aaaa = map.stream().collect(groupingBy(IPoint::y, toSet()));
        aaaa.forEach((key, value) -> {
            log(key + " - " + value.toString());
        });
    }

    private void transform(List<Point> shape, int x, int y) {
        for (Point point : shape) {
            point.x += x;
            point.y += y;
        }
    }

    private void moveShape(List<Point> shape, char move) {
        if (shapeCanMove(shape, move)) {
            for (Point point : shape) {
                if (move == '<') {
                    point.x -= 1;
                }
                if (move == '>') {
                    point.x += 1;
                }
            }
        }
    }

    private void moveShapeDown(List<Point> shape) {
        for (Point point : shape) {
            point.y -= 1;
        }
    }

    private boolean shapeCanMoveDown(List<Point> shape) {
        for (Point point : shape) {
            if (point.y > 0) {
                for (int i = map.size() - 1; i >= 0; i--) {
                    IPoint m = map.get(i);
                    if (point.x == m.x && point.y - 1 == m.y) {
                        return false;
                    }
                }
            } else {
                return false;
            }
        }
        return true;
    }

    private boolean shapeCanMove(List<Point> shape, char move) {
        for (Point point : shape) {
            if (move == '<') {
                if (point.x - 1 >= 0) {
                    for (int i = map.size() - 1; i >= 0; i--) {
                        IPoint m = map.get(i);
                        if (point.x - 1 == m.x && point.y == m.y) {
                            return false;
                        }
                    }
                } else {
                    return false;
                }
            }
            if (move == '>') {
                if (point.x + 1 < 7) {
                    for (int i = map.size() - 1; i >= 0; i--) {
                        IPoint m = map.get(i);
                        if (point.x + 1 == m.x && point.y == m.y) {
                            return false;
                        }
                    }
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    private List<Point> getNextShape() {
        List<IPoint> nextShape = shapes.get(shape);
        shape = (shape + 1) % shapes.size();
        return nextShape.stream().map(p -> new Point(p.x, p.y)).toList();
    }

    private char getNextMove(boolean moveDown) {
        if (moveDown) {
            return 'v';
        } else {
            char move = jets.charAt(jet);
            jet = (jet + 1) % jets.length();
            return move;
        }
    }

    private static class Point {
        private int x;
        private int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "[%d:%d]".formatted(x, y);
        }
    }

    private record IPoint(int x, int y) {
        @Override
        public String toString() {
            return "[%d:%d]".formatted(x, y);
        }
    }
}
