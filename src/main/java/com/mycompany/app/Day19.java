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
        return "" + blueprints.parallelStream().mapToLong(b -> findMacQualityNumberRecursive(b, 24)).sum();
    }

    @Override
    public String calculateSecondStar() {
        return "" + blueprints.parallelStream()
                              .filter(b -> b.id < 4)
                              .mapToLong(b -> findMacQualityNumberRecursive(b, 32))
                              .reduce((a, b) -> a * b).getAsLong();
    }

    private Long findMacQualityNumberRecursive(Blueprint blueprint, int time) {
        State initialState = new State(0, 0, 0, 0, 0, 1, 0, 0, 0);
        long start = System.currentTimeMillis();
        long result = blueprint.id * findRecursive(blueprint, initialState, time);
        long end = System.currentTimeMillis();
        System.out.println("#%d :\t%d\tTime: %d".formatted(blueprint.id, result, (end - start) / 1000));
        return result;
    }

    private long findRecursive(Blueprint blueprint, State state, int time) {
        // Until time is up.
        if (state.time == time - 1) {
            return state.geode + state.geodeRobots;
        }
        // Create.
        Set<State> states = new HashSet<>(5);
        int t = time - state.time;
        int nextTime = state.time + 1;
        // Ore robots.
        if (state.ore >= blueprint.oreRobotCostOre && state.oreRobots * t + state.ore < t * blueprint.maxRobotCostOre) {
            int newOre = state.ore + state.oreRobots - blueprint.oreRobotCostOre;
            int newClay = state.clay + state.clayRobots;
            int newObsidian = state.obsidian + state.obsidianRobots;
            int newGeode = state.geode + state.geodeRobots;
            int newOreRobots = state.oreRobots + 1;
            states.add(new State(nextTime, newOre, newClay, newObsidian, newGeode, newOreRobots, state.clayRobots, state.obsidianRobots, state.geodeRobots));
        }
        // Clay robots.
        if (state.ore >= blueprint.clayRobotCostOre && state.clayRobots * t + state.clay < t * blueprint.obsidianRobotCostClay) {
            int newOre = state.ore + state.oreRobots - blueprint.clayRobotCostOre;
            int newClay = state.clay + state.clayRobots;
            int newObsidian = state.obsidian + state.obsidianRobots;
            int newGeode = state.geode + state.geodeRobots;
            int newClayRobots = state.clayRobots + 1;
            states.add(new State(nextTime, newOre, newClay, newObsidian, newGeode, state.oreRobots, newClayRobots, state.obsidianRobots, state.geodeRobots));
        }
        // Obsidian robots.
        if (state.ore >= blueprint.obsidianRobotCostOre && state.clay >= blueprint.obsidianRobotCostClay && state.obsidianRobots * t + state.obsidian < t * blueprint.geodeRobotCostObsidian) {
            int newOre = state.ore + state.oreRobots - blueprint.obsidianRobotCostOre;
            int newClay = state.clay + state.clayRobots - blueprint.obsidianRobotCostClay;
            int newObsidian = state.obsidian + state.obsidianRobots;
            int newGeode = state.geode + state.geodeRobots;
            int newObsidianRobots = state.obsidianRobots + 1;
            states.add(new State(nextTime, newOre, newClay, newObsidian, newGeode, state.oreRobots, state.clayRobots, newObsidianRobots, state.geodeRobots));
        }
        // Geode robots.
        if (state.ore >= blueprint.geodeRobotCostOre && state.obsidian >= blueprint.geodeRobotCostObsidian) {
            int newOre = state.ore + state.oreRobots - blueprint.geodeRobotCostOre;
            int newClay = state.clay + state.clayRobots;
            int newObsidian = state.obsidian + state.obsidianRobots - blueprint.geodeRobotCostObsidian;
            int newGeode = state.geode + state.geodeRobots;
            int newGeodeRobots = state.geodeRobots + 1;
            states.add(new State(nextTime, newOre, newClay, newObsidian, newGeode, state.oreRobots, state.clayRobots, state.obsidianRobots, newGeodeRobots));
        }
        // Do nothing.
        int newOre = state.ore + state.oreRobots;
        int newClay = state.clay + state.clayRobots;
        int newObsidian = state.obsidian + state.obsidianRobots;
        int newGeode = state.geode + state.geodeRobots;
        states.add(new State(nextTime, newOre, newClay, newObsidian, newGeode, state.oreRobots, state.clayRobots, state.obsidianRobots, state.geodeRobots));
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
