package com.mycompany.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mycompany.app.FileReader.readInput;
import static java.lang.Integer.parseInt;
import static java.util.regex.Pattern.compile;

public class Day14 implements Day {
    private static final Pattern coordinatesPattern = compile("\\d+,\\d+");

    private final List<List<Point>> wallsList = new ArrayList<>();
    private final Point sandSource = new Point(500, 0);
    private List<Point> sand = new ArrayList<>();
    private int lowestFloor = 0;
    private int leftInfinity = 0;
    private int rightInfinity = 0;

    private final String filename;
    private List<String> input;

    public Day14(String filename) {
        this.filename = filename;
    }

    @Override
    public void loadData() throws IOException {
        input = readInput(filename);
        prepareData();
    }

    private void prepareData() {
        for (String line : input) {
            List<Point> wall = new ArrayList<>();
            Matcher coordinatesMatcher = coordinatesPattern.matcher(line);
            while (coordinatesMatcher.find()) {
                String[] wallCoordinates = coordinatesMatcher.group(0).split(",");
                lowestFloor = Math.max(lowestFloor, parseInt(wallCoordinates[1]));
                wall.add(new Point(parseInt(wallCoordinates[0]), parseInt(wallCoordinates[1])));
            }
            wallsList.add(wall);
        }
        leftInfinity = 500 - 4 * lowestFloor;
        rightInfinity = 500 + 4 * lowestFloor;
    }

    @Override
    public String calculateFirstStar() {
        Point grain = new Point(sandSource.x, sandSource.y);
        boolean grainMoved;
        while (true) {
            do {
                Point nextPosition = calculateNextPosition(grain);
                grainMoved = grain.x != nextPosition.x || grain.y != nextPosition.y;
                if (grainMoved) {
                    grain = nextPosition;
                } else {
                    grainMoved = false;
                }
                if (grain.y >= lowestFloor) {
                    grainMoved = false;
                }
            } while (grainMoved);
            if (grain.x == sandSource.x && grain.y == sandSource.y) {
                break;
            }
            if (grain.y < lowestFloor) {
                sand.add(grain);
            } else {
                break;
            }
            grain = new Point(sandSource.x, sandSource.y);
            grainMoved = true;
        }
        return "" + sand.size();
    }

    private Point calculateNextPosition(Point grain) {
        if (!isOccupied(grain.x, grain.y + 1)) {
            return new Point(grain.x, grain.y + 1);
        }
        if (!isOccupied(grain.x - 1, grain.y + 1)) {
            return new Point(grain.x - 1, grain.y + 1);
        }
        if (!isOccupied(grain.x + 1, grain.y + 1)) {
            return new Point(grain.x + 1, grain.y + 1);
        }
        return grain;
    }

    private boolean isOccupied(int x, int y) {
        for (List<Point> wall : wallsList) {
            for (int i = 1; i < wall.size(); i++) {
                Point from = wall.get(i - 1);
                Point to = wall.get(i);
                if ((from.x == x && to.x == x) && ((from.y <= y && y <= to.y) || (to.y <= y && y <= from.y))) {
                    return true;
                }
                if ((from.y == y && to.y == y) && ((from.x <= x && x <= to.x) || (to.x <= x && x <= from.x))) {
                    return true;
                }
            }
        }
        for (Point grain : sand) {
            if (grain.x == x && grain.y == y) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String calculateSecondStar() {
        wallsList.add(List.of(new Point(leftInfinity, lowestFloor + 2), new Point(rightInfinity, lowestFloor + 2)));
        lowestFloor = lowestFloor + 2;
        sand = new ArrayList<>();
        while (true) {
            Point grain = new Point(sandSource.x, sandSource.y);
            while (true) {
                Point nextPosition = calculateNextPosition(grain);
                if (grain.x != nextPosition.x || grain.y != nextPosition.y) {
                    grain = nextPosition;
                } else {
                    break;
                }
            }
            sand.add(grain);
            if (grain.x == sandSource.x && grain.y == sandSource.y) {
                break;
            }
        }
        return "" + sand.size();
    }

    private record Point(int x, int y) {
    }
}
