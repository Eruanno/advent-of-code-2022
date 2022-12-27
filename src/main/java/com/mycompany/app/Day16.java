package com.mycompany.app;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mycompany.app.FileReader.readInput;
import static java.lang.Math.max;
import static java.util.Arrays.stream;
import static java.util.Comparator.naturalOrder;
import static java.util.stream.Collectors.joining;

public class Day16 implements Day {

    private final Pattern pressurePattern = Pattern.compile("-?\\d+");
    private final Pattern valvesPattern = Pattern.compile("[A-Z][A-Z]");

    private final Map<String, List<String>> tunnelsGraph = new HashMap<>();
    private final Map<String, Integer> pressureValues = new HashMap<>();

    private final String filename;
    private List<String> input;

    public Day16(String filename) throws IOException {
        this.filename = filename;
        loadData();
    }

    private void loadData() throws IOException {
        input = readInput(filename);
        prepareData();
    }

    private void prepareData() {
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

    // 1704 - 1804
    // not 1718, 1815
    @Override
    public String calculateFirstStar() {
        List<State> results = new ArrayList<>();
        Queue<State> toVisit = new LinkedList<>();
        toVisit.add(new State("AA", "", 0, 0, 0));
        long maxPressure = Integer.MIN_VALUE;
        while (!toVisit.isEmpty()) {
            State state = toVisit.poll();
            if (state.time > 30) {
                results.add(state);
                maxPressure = max(maxPressure, state.releasedPressure);
            } else {
                if (!state.openedValves.contains(state.label) && pressureValues.get(state.label) > 0) {
                    long relPressure = state.releasedPressure + state.incPressure;
                    long incPressure = state.incPressure + pressureValues.get(state.label);
                    String[] openedValves = (state.openedValves + "," + state.label).split(",");
                    State nextState = new State(state.label, stream(openedValves)
                            .sorted(naturalOrder())
                            .collect(joining(",")), state.time + 1, incPressure, relPressure);
                    if (stateCanBeOffered(toVisit, nextState)) {
                        toVisit.offer(nextState);
                    }
                }
                for (String tunnel : tunnelsGraph.get(state.label)) {
                    State nextState = new State(tunnel, state.openedValves, state.time + 1, state.incPressure, state.releasedPressure + state.incPressure);
                    if (stateCanBeOffered(toVisit, nextState)) {
                        toVisit.offer(nextState);
                    }
                }
            }
        }
        return "" + results.stream().mapToLong(State::releasedPressure).max().getAsLong();
    }

    private boolean stateCanBeOffered(Queue<State> queue, State state) {
        if (queue.contains(state)) {
            return false;
        }
        /*
         * test data
         * if ((state.time > 10 && state.releasedPressure < 200) ||
         *(state.time > 13 && state.releasedPressure < 350) ||
         *(state.time > 16 && state.releasedPressure < 500) ||
         *(state.time > 19 && state.releasedPressure < 750) ||
         *(state.time > 22 && state.releasedPressure < 950) ||
         *(state.time > 25 && state.releasedPressure < 1200)) {
         *continue;
         *}
         */
        if ((state.time > 10 && state.releasedPressure < 97) //197
                //||(state.time > 11 && state.releasedPressure < 145) //245
                //||(state.time > 12 && state.releasedPressure < 193) //293
                //||(state.time > 13 && state.releasedPressure < 241)//341
                //||(state.time > 14 && state.releasedPressure < 305)//405
                || (state.time > 15 && state.releasedPressure < 369)//469
            //||(state.time > 16 && state.releasedPressure < 433)//533
            //||(state.time > 17 && state.releasedPressure < 519)//619
            //||(state.time > 18 && state.releasedPressure < 605)//705
            //||(state.time > 19 && state.releasedPressure < 691)//791
            //||(state.time > 20 && state.releasedPressure < 777)//877
            //||(state.time > 21 && state.releasedPressure < 863)//963
            //|| (state.time > 22 && state.releasedPressure < 949)//1049
            //|| (state.time > 23 && state.releasedPressure < 1035)
            //|| (state.time > 24 && state.releasedPressure < 1121)
            //|| (state.time > 25 && state.releasedPressure < 1207)
            //|| (state.time > 26 && state.releasedPressure < 1293)//1393
            //|| (state.time > 27 && state.releasedPressure < 1382)
            //|| (state.time > 28 && state.releasedPressure < 1493)
            //|| (state.time > 29 && state.releasedPressure < 1604)
        ) {
            return false;
        }
        return true;
    }

    @Override
    public String calculateSecondStar() {
        List<StateE> results = new ArrayList<>();
        Queue<StateE> toVisit = new LinkedList<>();
        toVisit.add(new StateE("AA", "AA", "", 0, 0, 0));
        long maxPressure = Integer.MIN_VALUE;
        while (!toVisit.isEmpty()) {
            StateE state = toVisit.poll();
            if (state.time > 26) {
                results.add(state);
                maxPressure = max(maxPressure, state.releasedPressure);
            } else {
                if (!state.openedValves.contains(state.player) && pressureValues.get(state.player) > 0 && !state.openedValves.contains(state.elephant) && pressureValues.get(state.elephant) > 0) {
                    long relPressure = state.releasedPressure + state.incPressure;
                    long incPressure = state.incPressure + pressureValues.get(state.player) + pressureValues.get(state.elephant);
                    String[] openedValves = (state.openedValves + "," + state.player + "," + state.elephant).split(",");
                    StateE nextState = new StateE(state.player, state.elephant, stream(openedValves)
                            .sorted(naturalOrder())
                            .collect(joining(",")), state.time + 1, incPressure, relPressure);
                    if (!toVisit.contains(nextState)) {
                        toVisit.offer(nextState);
                    }
                } else if (!state.openedValves.contains(state.player) && pressureValues.get(state.player) > 0) {
                    long relPressure = state.releasedPressure + state.incPressure;
                    long incPressure = state.incPressure + pressureValues.get(state.player);
                    String[] openedValves = (state.openedValves + "," + state.player).split(",");
                    for (String tunnelE : tunnelsGraph.get(state.elephant)) {
                        StateE nextState = new StateE(state.player, tunnelE, stream(openedValves)
                                .sorted(naturalOrder())
                                .collect(joining(",")), state.time + 1, incPressure, relPressure);
                        if (!toVisit.contains(nextState)) {
                            toVisit.offer(nextState);
                        }
                    }
                } else if (!state.openedValves.contains(state.elephant) && pressureValues.get(state.elephant) > 0) {
                    long relPressure = state.releasedPressure + state.incPressure;
                    long incPressure = state.incPressure + pressureValues.get(state.elephant);
                    String[] openedValves = (state.openedValves + "," + state.elephant).split(",");
                    for (String tunnel : tunnelsGraph.get(state.player)) {
                        StateE nextState = new StateE(tunnel, state.elephant, stream(openedValves)
                                .sorted(naturalOrder())
                                .collect(joining(",")), state.time + 1, incPressure, relPressure);
                        if (!toVisit.contains(nextState)) {
                            toVisit.offer(nextState);
                        }
                    }
                }
                if (everythingIsOpened(state.openedValves)) {
                    StateE nextState = new StateE(state.player, state.elephant, state.openedValves, state.time + 1, state.incPressure, state.releasedPressure + state.incPressure);
                    if (!toVisit.contains(nextState)) {
                        toVisit.offer(nextState);
                    }
                } else {
                    for (String tunnel : tunnelsGraph.get(state.player)) {
                        for (String tunnelE : tunnelsGraph.get(state.elephant)) {
                            StateE nextState = new StateE(tunnel, tunnelE, state.openedValves, state.time + 1, state.incPressure, state.releasedPressure + state.incPressure);
                            if (!toVisit.contains(nextState)) {
                                toVisit.offer(nextState);
                            }
                        }
                    }
                }
            }
        }
        return "" + results.stream().mapToLong(StateE::releasedPressure).max().getAsLong();
    }

    private boolean everythingIsOpened(String openedValves) {
        for (Map.Entry<String, Integer> entry : pressureValues.entrySet()) {
            if (entry.getValue() > 0) {
                if (!openedValves.contains(entry.getKey())) {
                    return false;
                }
            }
        }
        return true;
    }

    private record State(String label, String openedValves, int time, long incPressure, long releasedPressure) {
    }

    private record StateE(String player, String elephant, String openedValves, int time, long incPressure,
                          long releasedPressure) {
    }
}
