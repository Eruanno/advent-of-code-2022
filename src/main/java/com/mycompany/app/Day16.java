package com.mycompany.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mycompany.app.FileReader.readInput;
import static com.mycompany.app.Logger.log;

public class Day16 implements Day {

    private final Pattern pressurePattern = Pattern.compile("-?\\d+");
    private final Pattern valvesPattern = Pattern.compile("[A-Z][A-Z]");

    private String filename = "day-16-test";
    private List<String> input;
    private final List<Valve> graph = new ArrayList<>();

    public void solve() throws IOException {
        loadInput();
        prepareData();
        log("First star:");
        log(calculateFirstStar()); // 1651
        log("Second star:");
        log(calculateSecondStar());
    }

    void loadInput() throws IOException {
        input = readInput(filename);
    }

    void prepareData() {
        for (String line : input) {
            Matcher pressureMatcher = pressurePattern.matcher(line);
            pressureMatcher.find();
            int pressure = Integer.parseInt(pressureMatcher.group(0));

            Matcher valvesMatcher = valvesPattern.matcher(line);
            valvesMatcher.find();
            String label = valvesMatcher.group(0);
            List<String> tunnels = new ArrayList<>();
            while (valvesMatcher.find()) {
                tunnels.add(valvesMatcher.group(0));
            }
            graph.add(new Valve(label, pressure, false, tunnels));
        }
    }

    void setFilename(String filename) {
        this.filename = filename;
    }

    Long calculateFirstStar() {
        Valve valve = graph.get(0);
        long totalPressure = 0;
        long accPressure = 0;
        valve.opened = true;
        for (int m = 0; m < 30; m++) {
            if (!valve.opened) {
                log("Opening valve " + valve.label);
                valve.opened = true;
                accPressure += valve.pressure;
            } else {
                Optional<Valve> next = findNextValve(valve.tunnels);
                if (next.isPresent()) {
                    valve = next.get();
                }
                log("Moving to valve " + valve.label);
            }
            log("Releasing pressure : " + accPressure);
            totalPressure += accPressure;
        }
        return totalPressure;
    }

    private Optional<Valve> findNextValve(List<String> tunnels) {
        boolean everythingIsOpened = graph.stream().filter(Valve::isOpened).toList().size() == graph.size();
        if (everythingIsOpened) {
            return Optional.empty();
        }
        List<Valve> neighbours = tunnels.stream()
                                        .map(tunnel -> graph.stream()
                                                            .filter(valve -> valve.label.equals(tunnel))
                                                            .findFirst()
                                                            .get())
                                        .toList();
        Optional<Valve> maxPressureNotOpenedValve = Optional.empty();
        for (Valve v : neighbours) {
            if (v.isOpened()) {
                continue;
            }
            if (maxPressureNotOpenedValve.isEmpty()) {
                maxPressureNotOpenedValve = Optional.of(v);
            }
            if (maxPressureNotOpenedValve.get().pressure < v.pressure) {
                maxPressureNotOpenedValve = Optional.of(v);
            }
        }
        return maxPressureNotOpenedValve;
    }

    Long calculateSecondStar() {
        return -1L;
    }

    private class Valve {
        private final String label;
        private final int pressure;
        private boolean opened;
        private List<String> tunnels;

        Valve(String label, int pressure, boolean opened, List<String> tunnels) {
            this.label = label;
            this.pressure = pressure;
            this.opened = opened;
            this.tunnels = tunnels;
        }

        boolean isOpened() {
            return opened;
        }

        List<String> getTunnels() {
            return tunnels;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Valve valve = (Valve) o;
            return pressure == valve.pressure && Objects.equals(label, valve.label);
        }

        @Override
        public int hashCode() {
            return Objects.hash(label, pressure);
        }
    }
}
