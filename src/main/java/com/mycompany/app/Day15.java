package com.mycompany.app;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
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
        List<Point> intersections = new ArrayList<>();
        // https://fypandroid.wordpress.com/2011/07/03/how-to-calculate-the-intersection-of-two-circles-java/
        for (int i = 0; i < sensors.size() - 1; i++) {
            for (int j = 1; j < sensors.size(); j++) {
                Sensor a = sensors.get(i);
                Sensor b = sensors.get(j);
                BigDecimal d = BigDecimal.valueOf(a.position.x - b.position.x * (a.position.x - b.position.x)
                        + (a.position.y - b.position.y) * (a.position.y - b.position.y)).sqrt(MathContext.DECIMAL64);
                BigDecimal ra = BigDecimal.valueOf(calculateDistance(a.position, a.beacon));
                BigDecimal rb = BigDecimal.valueOf(calculateDistance(b.position, b.beacon));
                if (d.signum() != 0 && d.compareTo(ra.add(rb)) < 0) {
                    BigDecimal ra2 = ra.pow(2);
                    BigDecimal rb2 = rb.pow(2);
                    BigDecimal d2 = d.pow(2);
                    BigDecimal d1 = (ra2.subtract(rb2)
                                        .add(d2)).divide(d.multiply(BigDecimal.valueOf(2)), RoundingMode.HALF_DOWN);
                    BigDecimal d12 = d1.pow(2);
                    BigDecimal h = ra2.subtract(d12).sqrt(MathContext.DECIMAL64);
                    BigDecimal x3 = BigDecimal.valueOf(a.position.x)
                                              .add(d1.multiply(BigDecimal.valueOf((b.position.x - a.position.x))))
                                              .divide(d, RoundingMode.HALF_DOWN);
                    BigDecimal y3 = BigDecimal.valueOf(a.position.y)
                                              .add(d1.multiply(BigDecimal.valueOf((b.position.y - a.position.y))))
                                              .divide(d, RoundingMode.HALF_DOWN);
                    BigDecimal x4_i = x3.add(h.multiply(BigDecimal.valueOf(b.position.y - a.position.y)))
                                        .divide(d, RoundingMode.HALF_DOWN);
                    BigDecimal y4_i = y3.subtract(h.multiply(BigDecimal.valueOf(b.position.x - a.position.x)))
                                        .divide(d, RoundingMode.HALF_DOWN);
                    BigDecimal x4_ii = x3.subtract(h.multiply(BigDecimal.valueOf(b.position.y - a.position.y)))
                                         .divide(d, RoundingMode.HALF_DOWN);
                    BigDecimal y4_ii = y3.add(h.multiply(BigDecimal.valueOf(b.position.x - a.position.x)))
                                         .divide(d, RoundingMode.HALF_DOWN);
                    if (x4_i.longValue() >= 0 && y4_i.longValue() >= 0 && x4_ii.longValue() >= 0 && y4_ii.longValue() >= 0) {
                        if (x4_i.longValue() < 4000000 && y4_i.longValue() < 4000000 && x4_ii.longValue() < 4000000 && y4_ii.longValue() < 4000000) {
                            intersections.add(new Point(x4_i.longValue(), y4_i.longValue()));
                            intersections.add(new Point(x4_ii.longValue(), y4_ii.longValue()));
                        }
                    }
                }
            }
        }
        for (Point point : intersections) {
            boolean scanned = false;
            for (Sensor sensor : sensors) {
                long distanceToBeacon = calculateDistance(sensor.position, sensor.beacon);
                long distanceToCell = calculateDistance(sensor.position, new Point(point.y, point.x));
                if (distanceToBeacon >= distanceToCell) {
                    scanned = true;
                    break;
                }
            }
            if (!scanned) {
                return point.x * 4000000L + point.y;
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
