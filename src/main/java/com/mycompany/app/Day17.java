package com.mycompany.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.mycompany.app.FileReader.readInput;

public class Day17 implements Day {

    private final List<List<IPoint>> shapes = new ArrayList<>();
    private final List<Row> history = new ArrayList<>();
    private List<IPoint> tower = new ArrayList<>();
    private String jets;
    private int jetIndex = 0;
    private int shapeIndex = 0;
    private long highestFloor = 0;

    private final String filename;
    private List<String> input;

    public Day17(String filename) throws IOException {
        this.filename = filename;
        loadData();
    }

    private void loadData() throws IOException {
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
        long shapeX = 2;
        long shapeY = highestFloor + 3;
        transform(shape, shapeX, shapeY);
        boolean moveDown = false;
        boolean warped = false;
        char move = getNextMove(moveDown);
        long previousDeltaRocks = 0;
        long previousDeltaHeight = 0;
        for (long currentRock = 0; currentRock < numberOfRocks; ) {
            if (!moveDown) {
                moveShape(shape, move);
                move = getNextMove(moveDown);
                moveDown = true;
            } else {
                if (shapeCanMoveDown(shape)) {
                    moveShapeDown(shape);
                } else {
                    Optional<Row> row = history.stream()
                                               .filter(r -> r.jetIndex == jetIndex)
                                               .sorted((a, b) -> Math.toIntExact(b.height - a.height))
                                               .findFirst();
                    if (this.shapeIndex == 0) {
                        history.add(new Row(currentRock, jetIndex, highestFloor));
                    }
                    tower.addAll(shape.stream().map(p -> new IPoint(p.x, p.y)).toList());
                    highestFloor = tower.stream().map(p -> p.y).max(Long::compareTo).get();
                    if (!warped && row.isPresent()) {
                        long deltaRocks = currentRock - row.get().totalRocks;
                        long deltaHeight = highestFloor - row.get().height;
                        if (previousDeltaRocks == deltaRocks && previousDeltaHeight == deltaHeight) {
                            long delta = (numberOfRocks - currentRock) / deltaRocks;
                            currentRock = currentRock + delta * deltaRocks;
                            highestFloor = highestFloor + delta * deltaHeight;
                            tower = tower.stream()
                                         .map(p -> new IPoint(p.x, p.y + delta * deltaHeight))
                                         .collect(Collectors.toList());
                            warped = true;
                        } else {
                            previousDeltaRocks = deltaRocks;
                            previousDeltaHeight = deltaHeight;
                        }
                    }
                    while (tower.size() > 100) {
                        tower.remove(0);
                    }
                    shape = getNextShape();
                    shapeY = highestFloor + 4;
                    transform(shape, shapeX, shapeY);
                    currentRock++;
                }
                moveDown = false;
            }
        }
        return "" + (highestFloor + 1);
    }

    private void transform(List<Point> shape, long x, long y) {
        for (Point point : shape) {
            point.x += x;
            point.y += y;
        }
    }

    private void moveShape(List<Point> shape, char direction) {
        if (shapeCanMove(shape, direction)) {
            for (Point point : shape) {
                if (direction == '<') {
                    point.x -= 1;
                }
                if (direction == '>') {
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
                for (int i = tower.size() - 1; i >= 0; i--) {
                    IPoint m = tower.get(i);
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

    private boolean shapeCanMove(List<Point> shape, char direction) {
        for (Point point : shape) {
            if (direction == '<') {
                if (point.x - 1 >= 0) {
                    for (int i = tower.size() - 1; i >= 0; i--) {
                        IPoint m = tower.get(i);
                        if (point.x - 1 == m.x && point.y == m.y) {
                            return false;
                        }
                    }
                } else {
                    return false;
                }
            }
            if (direction == '>') {
                if (point.x + 1 < 7) {
                    for (int i = tower.size() - 1; i >= 0; i--) {
                        IPoint m = tower.get(i);
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
        List<IPoint> nextShape = shapes.get(shapeIndex);
        shapeIndex = (shapeIndex + 1) % shapes.size();
        return nextShape.stream().map(p -> new Point(p.x, p.y)).toList();
    }

    private char getNextMove(boolean moveDown) {
        if (moveDown) {
            return 'v';
        } else {
            char move = jets.charAt(jetIndex);
            jetIndex = (jetIndex + 1) % jets.length();
            return move;
        }
    }

    private static class Point {
        private long x;
        private long y;

        Point(long x, long y) {
            this.x = x;
            this.y = y;
        }
    }

    private record IPoint(long x, long y) {
    }

    private record Row(long totalRocks, int jetIndex, long height) {
    }
}
