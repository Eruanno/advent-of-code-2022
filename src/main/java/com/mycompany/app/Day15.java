package com.mycompany.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mycompany.app.FileReader.readInput;
import static com.mycompany.app.Logger.log;
import static java.lang.Math.*;

public class Day15 implements Day {

    private final Pattern p = Pattern.compile("-?\\d+");
    private final List<Sensor> sensors = new ArrayList<>();
    private String filename = "day-15";
    private List<String> input;
    long left = Integer.MAX_VALUE;
    long right = Integer.MIN_VALUE;

    public void solve() throws IOException {
        loadInput();
        prepareData();
        log("First star:");
        log(calculateFirstStar(12));
        log("Second star:");
        log(calculateSecondStar(20));
    }

    void loadInput() throws IOException {
        input = readInput(filename);
    }

    void prepareData() {
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
            Sensor sensor = new Sensor(new Point(sensorX, sensorY), new Point(beaconX, beaconY));
            sensors.add(sensor);
            left = min(left, min(sensorX, beaconX));
            right = max(right, max(sensorX, beaconX));
            long distance = calculateDistance(sensor.position, sensor.beacon);
            left = min(left, sensor.position.x - distance);
            right = max(right, sensor.position.x + distance);
        }
    }

    void setFilename(String filename) {
        this.filename = filename;
    }

    Long calculateFirstStar(int row) {
        long scannedPositionsInRow = 0;
        for (long column = left; column <= right; column++) {
            for (Sensor sensor : sensors) {
                if (sensor.beacon.x == column && sensor.beacon.y == row) {
                    continue;
                }
                long distanceToBeacon = calculateDistance(sensor.position, sensor.beacon);
                long distanceToCell = calculateDistance(sensor.position, new Point(column, row));
                if (distanceToBeacon >= distanceToCell) {
                    scannedPositionsInRow++;
                    break;
                }
            }
        }
        return scannedPositionsInRow;
    }

    Long calculateSecondStar(int size) {
        for (int column = 0; column < size; column++) {
            for (int row = 0; row < size; row++) {
                boolean scanned = false;
                for (Sensor sensor : sensors) {
                    long distanceToBeacon = calculateDistance(sensor.position, sensor.beacon);
                    long distanceToCell = calculateDistance(sensor.position, new Point(column, row));
                    if (distanceToBeacon >= distanceToCell) {
                        scanned = true;
                        break;
                    }
                }
                if (!scanned) {
                    return column * 4000000L + row;
                }
            }
        }
        return -1L;
    }

    long calculateDistance(Point a, Point b) {
        return abs(a.x - b.x) + abs(a.y - b.y);
    }

    private record Point(long x, long y) {
    }

    private record Sensor(Point position, Point beacon) {
        @Override
        public String toString() {
            return "Sensor: %d, %d Beacon: %d, %d".formatted(position.x, position.y, beacon.x, beacon.y);
        }
    }
}
