package com.mycompany.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mycompany.app.FileReader.readInput;
import static java.lang.Math.*;

public class Day15 implements Day {

    private final Pattern p = Pattern.compile("-?\\d+");
    private final List<Sensor> sensors = new ArrayList<>();
    private long left = Integer.MAX_VALUE;
    private long right = Integer.MIN_VALUE;
    private final long row;
    private final long size;
    private final String filename;
    private List<String> input;

    public Day15(String filename, int row, int size) throws IOException {
        this.filename = filename;
        this.row = row;
        this.size = size;
        loadData();
    }

    private void loadData() throws IOException {
        input = readInput(filename);
        prepareData();
    }

    private void prepareData() {
        for (String line : input) {
            Matcher m = p.matcher(line);
            m.find();
            long sensorX = Long.parseLong(m.group(0));
            m.find();
            long sensorY = Long.parseLong(m.group(0));
            m.find();
            long beaconX = Long.parseLong(m.group(0));
            m.find();
            long beaconY = Long.parseLong(m.group(0));
            Point position = new Point(sensorX, sensorY);
            Point beacon = new Point(beaconX, beaconY);
            Sensor sensor = new Sensor(position, beacon, calculateDistance(position, beacon));
            sensors.add(sensor);
            left = min(left, min(sensorX, beaconX));
            right = max(right, max(sensorX, beaconX));
            long distance = calculateDistance(sensor.position, sensor.beacon);
            left = min(left, sensor.position.x - distance);
            right = max(right, sensor.position.x + distance);
        }
    }

    @Override
    public String calculateFirstStar() {
        long scannedPositionsInRow = 0;
        for (long column = left; column <= right; column++) {
            for (Sensor sensor : sensors) {
                if (sensor.beacon.x == column && sensor.beacon.y == row) {
                    continue;
                }
                long distanceToCell = calculateDistance(sensor.position, new Point(column, row));
                if (sensor.distance >= distanceToCell) {
                    scannedPositionsInRow++;
                    break;
                }
            }
        }
        return "" + scannedPositionsInRow;
    }

    @Override
    public String calculateSecondStar() {
        List<Point> perimeters = new ArrayList<>();
        for (Sensor sensor : sensors) {
            long d = sensor.distance + 1;
            for (long x = sensor.position.x - d, y = sensor.position.y; x <= sensor.position.x; x++, y++) {
                if (x >= 0 && y >= 0 && x < size && y < size) {
                    perimeters.add(new Point(x, y));
                }
            }
            for (long x = sensor.position.x, y = sensor.position.y + d; x <= sensor.position.x + d; x++, y--) {
                if (x >= 0 && y >= 0 && x < size && y < size) {
                    perimeters.add(new Point(x, y));
                }
            }
            for (long x = sensor.position.x + d, y = sensor.position.y + d; x <= sensor.position.x; x--, y--) {
                if (x >= 0 && y >= 0 && x < size && y < size) {
                    perimeters.add(new Point(x, y));
                }
            }
            for (long x = sensor.position.x, y = sensor.position.y; x <= sensor.position.x - d; x--, y++) {
                if (x >= 0 && y >= 0 && x < size && y < size) {
                    perimeters.add(new Point(x, y));
                }
            }
        }
        for (Point point : perimeters) {
            boolean scanned = false;
            for (Sensor sensor : sensors) {
                long distanceToCell = calculateDistance(sensor.position, point);
                if (sensor.distance >= distanceToCell) {
                    scanned = true;
                    break;
                }
            }
            if (!scanned) {
                return "" + (point.x * 4_000_000 + point.y);
            }
        }
        return "";
    }

    long calculateDistance(Point a, Point b) {
        return abs(a.x - b.x) + abs(a.y - b.y);
    }

    private record Point(long x, long y) {
    }

    private record Sensor(Point position, Point beacon, long distance) {
        @Override
        public String toString() {
            return "Sensor: %d, %d Beacon: %d, %d".formatted(position.x, position.y, beacon.x, beacon.y);
        }
    }
}
