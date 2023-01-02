package com.mycompany.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mycompany.app.FileReader.readInput;
import static java.util.Arrays.asList;
import static java.util.Collections.max;

public class Day19 implements Day {
    private final Pattern pattern = Pattern.compile("-?\\d+");

    private final List<Blueprint> blueprints = new ArrayList<>();

    private final String filename;
    private List<String> input;

    public Day19(String filename) throws IOException {
        this.filename = filename;
        loadData();
    }

    private void loadData() throws IOException {
        input = readInput(filename);
        prepareData();
    }

    private void prepareData() {
        for (String line : input) {
            Matcher matcher = pattern.matcher(line);
            matcher.find();
            int id = Integer.parseInt(matcher.group(0));
            matcher.find();
            int oreRobotCostOre = Integer.parseInt(matcher.group(0));
            matcher.find();
            int clayRobotCostOre = Integer.parseInt(matcher.group(0));
            matcher.find();
            int obsidianRobotCostOre = Integer.parseInt(matcher.group(0));
            matcher.find();
            int obsidianRobotCostClay = Integer.parseInt(matcher.group(0));
            matcher.find();
            int geodeRobotCostOre = Integer.parseInt(matcher.group(0));
            matcher.find();
            int geodeRobotCostObsidian = Integer.parseInt(matcher.group(0));
            blueprints.add(new Blueprint(id, oreRobotCostOre, clayRobotCostOre, obsidianRobotCostOre, obsidianRobotCostClay, geodeRobotCostOre, geodeRobotCostObsidian, max(asList(oreRobotCostOre, clayRobotCostOre, obsidianRobotCostOre, geodeRobotCostOre))));
        }
    }

    @Override
    public String calculateFirstStar() {
        return "" + blueprints.parallelStream().mapToLong(this::findMaxQualityNumber).sum();
    }

    private Long findMaxQualityNumber(Blueprint blueprint) {
        State initialState = new State(0, 0, 0, 0, 0, 1, 0, 0, 0);
        return blueprint.id * findRecursive(blueprint, initialState, 24);
    }

    @Override
    public String calculateSecondStar() {
        return "" + blueprints.parallelStream()
                              .filter(b -> b.id < 4)
                              .mapToLong(this::findMaxNumberOfGeodes)
                              .reduce((a, b) -> a * b).getAsLong();
    }

    private Long findMaxNumberOfGeodes(Blueprint blueprint) {
        State initialState = new State(0, 0, 0, 0, 0, 1, 0, 0, 0);
        return findRecursive(blueprint, initialState, 32);
    }

    private long findRecursive(Blueprint blueprint, State state, int time) {
        // Until time is up.
        if (state.time == time - 1) {
            return state.geode + state.geodeRobots;
        }
        // Build.
        Set<State> states = new HashSet<>(0);
        int remainingTime = time - state.time;
        int nextTime = state.time + 1;
        int nextOre = state.ore + state.oreRobots;
        int nextClay = state.clay + state.clayRobots;
        int nextObsidian = state.obsidian + state.obsidianRobots;
        int nextGeode = state.geode + state.geodeRobots;
        // Geode robots.
        if (state.ore >= blueprint.geodeRobotCostOre && state.obsidian >= blueprint.geodeRobotCostObsidian) {
            states.add(new State(nextTime, nextOre - blueprint.geodeRobotCostOre, nextClay, nextObsidian - blueprint.geodeRobotCostObsidian, nextGeode, state.oreRobots, state.clayRobots, state.obsidianRobots, state.geodeRobots + 1));
        } else {
            // Ore robots.
            if ((state.ore >= blueprint.oreRobotCostOre) && (state.oreRobots * remainingTime + state.ore < remainingTime * blueprint.maxRobotCostOre)) {
                states.add(new State(nextTime, nextOre - blueprint.oreRobotCostOre, nextClay, nextObsidian, nextGeode, state.oreRobots + 1, state.clayRobots, state.obsidianRobots, state.geodeRobots));
            }
            // Clay robots.
            if ((state.ore >= blueprint.clayRobotCostOre) && (state.clayRobots * remainingTime + state.clay < remainingTime * blueprint.obsidianRobotCostClay)) {
                states.add(new State(nextTime, nextOre - blueprint.clayRobotCostOre, nextClay, nextObsidian, nextGeode, state.oreRobots, state.clayRobots + 1, state.obsidianRobots, state.geodeRobots));
            }
            // Obsidian robots.
            if ((state.ore >= blueprint.obsidianRobotCostOre && state.clay >= blueprint.obsidianRobotCostClay) && (state.obsidianRobots * remainingTime + state.obsidian < remainingTime * blueprint.geodeRobotCostObsidian)) {
                states.add(new State(nextTime, nextOre - blueprint.obsidianRobotCostOre, nextClay - blueprint.obsidianRobotCostClay, nextObsidian, nextGeode, state.oreRobots, state.clayRobots, state.obsidianRobots + 1, state.geodeRobots));
            }
            // Do nothing.
            // | size | *t | *r | **t | **r |
            // |------|----|----|-----|-----|
            // | 0    | x  | x  | x   | v   |
            // | 1    | v  | x  | v   | v   |
            // | 2    | v  | v  | v   | v   |
            if (states.size() < 2) {
                states.add(new State(nextTime, nextOre, nextClay, nextObsidian, nextGeode, state.oreRobots, state.clayRobots, state.obsidianRobots, state.geodeRobots));
            }
        }
        // Make next step.
        return states.parallelStream()
                     .map(s -> findRecursive(blueprint, s, time))
                     .mapToLong(Long::longValue)
                     .max()
                     .getAsLong();
    }

    private record Blueprint(int id, int oreRobotCostOre, int clayRobotCostOre, int obsidianRobotCostOre,
                             int obsidianRobotCostClay, int geodeRobotCostOre, int geodeRobotCostObsidian,
                             int maxRobotCostOre) {
    }

    private record State(int time, int ore, int clay, int obsidian, int geode, int oreRobots, int clayRobots,
                         int obsidianRobots, int geodeRobots) {
    }
}
