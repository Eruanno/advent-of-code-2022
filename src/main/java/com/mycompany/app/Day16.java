package com.mycompany.app;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mycompany.app.FileReader.readInput;
import static com.mycompany.app.Logger.log;

public class Day16 implements Day {

    private final Pattern pressurePattern = Pattern.compile("-?\\d+");
    private final Pattern valvesPattern = Pattern.compile("[A-Z][A-Z]");

    private final Map<String, List<String>> tunnelsGraph = new HashMap<>();
    private final Map<String, Integer> pressureValues = new HashMap<>();

    private final String filename;
    private List<String> input;

    public Day16(String filename) {
        this.filename = filename;
    }

    @Override
    public void loadData() throws IOException {
        input = readInput(filename);
        prepareData();
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
            tunnelsGraph.put(label, tunnels);
            pressureValues.put(label, pressure);
        }
    }

    private record DequeValve(String label, String openedValves, int time, long incPressure, long releasedPressure) {
    }

    // 1704 - 1804
    // not 1718, 1815
    @Override
    public String calculateFirstStar() {
        long i = 0;
        List<DequeValve> timesUp = new ArrayList<>();
        for (int time = 10; time < 30; time++) {
            Deque<DequeValve> toVisit = new ArrayDeque<>();
            timesUp = new ArrayList<>();
            toVisit.add(new DequeValve("AA", "AA", 0, 0, 0));
            while (!toVisit.isEmpty()) {
                i++;
                DequeValve valve = toVisit.pop();
                if ((valve.time > 10 && valve.releasedPressure < 97) //197
                        //||(valve.time > 11 && valve.releasedPressure < 145) //245
                        //||(valve.time > 12 && valve.releasedPressure < 193) //293
                        //||(valve.time > 13 && valve.releasedPressure < 241)//341
                        //||(valve.time > 14 && valve.releasedPressure < 305)//405
                        || (valve.time > 15 && valve.releasedPressure < 369)//469
                        //||(valve.time > 16 && valve.releasedPressure < 433)//533
                        //||(valve.time > 17 && valve.releasedPressure < 519)//619
                        //||(valve.time > 18 && valve.releasedPressure < 605)//705
                        //||(valve.time > 19 && valve.releasedPressure < 691)//791
                        //||(valve.time > 20 && valve.releasedPressure < 777)//877
                        //||(valve.time > 21 && valve.releasedPressure < 863)//963
                        || (valve.time > 22 && valve.releasedPressure < 949)//1049
                        //|| (valve.time > 23 && valve.releasedPressure < 1035)
                        //|| (valve.time > 24 && valve.releasedPressure < 1121)
                        //|| (valve.time > 25 && valve.releasedPressure < 1207)
                        || (valve.time > 26 && valve.releasedPressure < 1293)//1393
                    //|| (valve.time > 27 && valve.releasedPressure < 1382)
                    //|| (valve.time > 28 && valve.releasedPressure < 1493)
                    //|| (valve.time > 29 && valve.releasedPressure < 1604)
                ) {
                    continue;
                }
                /*
                 * test data
                 * if ((valve.time > 10 && valve.releasedPressure < 200) ||
                 *(valve.time > 13 && valve.releasedPressure < 350) ||
                 *(valve.time > 16 && valve.releasedPressure < 500) ||
                 *(valve.time > 19 && valve.releasedPressure < 750) ||
                 *(valve.time > 22 && valve.releasedPressure < 950) ||
                 *(valve.time > 25 && valve.releasedPressure < 1200)) {
                 *continue;
                 *}
                 */
                if (valve.time > time) {
                    timesUp.add(valve);
                } else {
                    if (!valve.openedValves.contains(valve.label)) {
                        long relPressure = valve.releasedPressure + valve.incPressure;
                        long incPressure = valve.incPressure + pressureValues.get(valve.label);
                        toVisit.add(new DequeValve(valve.label, valve.openedValves + "," + valve.label, valve.time + 1, incPressure, relPressure));
                    }
                    for (String tunnel : tunnelsGraph.get(valve.label)) {
                        toVisit.add(new DequeValve(tunnel, valve.openedValves, valve.time + 1, valve.incPressure, valve.releasedPressure + valve.incPressure));
                    }
                }
            }
            long value = timesUp.stream().mapToLong(DequeValve::releasedPressure).max().getAsLong();
            log("Time: %d\t%d".formatted(time, value));
        }
        return "" + timesUp.stream().mapToLong(DequeValve::releasedPressure).max().getAsLong();
    }

    @Override
    public String calculateSecondStar() {
        return "";
    }
}
