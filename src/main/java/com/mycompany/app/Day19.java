package com.mycompany.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mycompany.app.FileReader.readInput;
import static java.lang.Math.max;

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
            blueprints.add(new Blueprint(id, oreRobotCostOre, clayRobotCostOre, obsidianRobotCostOre, obsidianRobotCostClay, geodeRobotCostOre, geodeRobotCostObsidian));
        }
    }

    @Override
    public String calculateFirstStar() {
        return "" + blueprints.parallelStream().mapToLong(this::findMacQualityNumber).sum();
    }

    private Long findMacQualityNumber(Blueprint blueprint) {
        Queue<State> queue = new LinkedList<>();
        queue.offer(new State(0, 0, 0, 0, 0, 1, 0, 0, 0));
        long maxResult = Integer.MIN_VALUE;
        int i = 0;
        while (!queue.isEmpty()) {
            State state = queue.poll();
            if(state.time == i) {
                System.out.println("#%d: Minutes elapsed: %d\tQueue size: %d\t".formatted(blueprint.id, i, queue.size()));
                i++;
            }
            if (state.time == 24) {
                maxResult = max(maxResult, state.geode);
                System.out.println(maxResult);
            } else {
                if (state.ore >= blueprint.oreRobotCostOre) {
                    int newOre = state.ore + state.oreRobots - blueprint.oreRobotCostOre;
                    int newClay = state.clay + state.clayRobots;
                    int newObsidian = state.obsidian + state.obsidianRobots;
                    int newGeode = state.geode + state.geodeRobots;
                    int newOreRobots = state.oreRobots + 1;
                    State nextState = new State(state.time + 1, newOre, newClay, newObsidian, newGeode, newOreRobots, state.clayRobots, state.obsidianRobots, state.geodeRobots);
                    if (!queue.contains(nextState)) {
                        queue.offer(nextState);
                    }
                }
                if (state.ore >= blueprint.clayRobotCostOre) {
                    int newOre = state.ore + state.oreRobots - blueprint.clayRobotCostOre;
                    int newClay = state.clay + state.clayRobots;
                    int newObsidian = state.obsidian + state.obsidianRobots;
                    int newGeode = state.geode + state.geodeRobots;
                    int newClayRobots = state.clayRobots + 1;
                    State nextState = new State(state.time + 1, newOre, newClay, newObsidian, newGeode, state.oreRobots, newClayRobots, state.obsidianRobots, state.geodeRobots);
                    if (!queue.contains(nextState)) {
                        queue.offer(nextState);
                    }
                }
                if (state.ore >= blueprint.obsidianRobotCostOre && state.clay >= blueprint.obsidianRobotCostClay) {
                    int newOre = state.ore + state.oreRobots - blueprint.obsidianRobotCostOre;
                    int newClay = state.clay + state.clayRobots - blueprint.obsidianRobotCostClay;
                    int newObsidian = state.obsidian + state.obsidianRobots;
                    int newGeode = state.geode + state.geodeRobots;
                    int newObsidianRobots = state.obsidianRobots + 1;
                    State nextState = new State(state.time + 1, newOre, newClay, newObsidian, newGeode, state.oreRobots, state.clayRobots, newObsidianRobots, state.geodeRobots);
                    if (!queue.contains(nextState)) {
                        queue.offer(nextState);
                    }
                }
                if (state.ore >= blueprint.geodeRobotCostOre && state.obsidian >= blueprint.geodeRobotCostObsidian) {
                    int newOre = state.ore + state.oreRobots - blueprint.geodeRobotCostOre;
                    int newClay = state.clay + state.clayRobots;
                    int newObsidian = state.obsidian + state.obsidianRobots - blueprint.geodeRobotCostObsidian;
                    int newGeode = state.geode + state.geodeRobots;
                    int newGeodeRobots = state.geodeRobots + 1;
                    State nextState = new State(state.time + 1, newOre, newClay, newObsidian, newGeode, state.oreRobots, state.clayRobots, state.obsidianRobots, newGeodeRobots);
                    if (!queue.contains(nextState)) {
                        queue.offer(nextState);
                    }
                }
                int newOre = state.ore + state.oreRobots;
                int newClay = state.clay + state.clayRobots;
                int newObsidian = state.obsidian + state.obsidianRobots;
                int newGeode = state.geode + state.geodeRobots;
                State nextState = new State(state.time + 1, newOre, newClay, newObsidian, newGeode, state.oreRobots, state.clayRobots, state.obsidianRobots, state.geodeRobots);
                if (!queue.contains(nextState)) {
                    queue.offer(nextState);
                }
            }
        }
        return blueprint.id * maxResult;
    }

    @Override
    public String calculateSecondStar() {
        return "test";
    }

    private record Blueprint(int id, int oreRobotCostOre, int clayRobotCostOre, int obsidianRobotCostOre,
                             int obsidianRobotCostClay, int geodeRobotCostOre, int geodeRobotCostObsidian) {
    }

    private record State(int time, int ore, int clay, int obsidian, int geode, int oreRobots, int clayRobots,
                         int obsidianRobots, int geodeRobots) {
    }
}
