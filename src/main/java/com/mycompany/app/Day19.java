package com.mycompany.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mycompany.app.FileReader.readInput;
import static com.mycompany.app.Logger.log;
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
            int geodeRobotCostOreObsidian = Integer.parseInt(matcher.group(0));
            blueprints.add(new Blueprint(id, oreRobotCostOre, clayRobotCostOre, obsidianRobotCostOre, obsidianRobotCostClay, geodeRobotCostOre, geodeRobotCostOreObsidian));
        }
    }

    @Override
    public String calculateFirstStar() {
        return "" + blueprints.stream().mapToLong(this::findMacQualityNumber).sum();
    }

    private Long findMacQualityNumber(Blueprint blueprint) {
        long result = 0;
        for (int x = 1; x < 9; x++) {
            for (int y = 1; y < 9; y++) {
                for (int z = 1; z < 9; z++) {
                    result = max(result, calculateQualityNumber(blueprint, x, y, z));
                }
            }
        }
        return result;
    }

    private Long calculateQualityNumber(Blueprint blueprint, int maxOreRobots, int maxClayRobots, int maxObsidianRobots) {
        long ore = 0;
        long clay = 0;
        long obsidian = 0;
        long geode = 0;
        long oreRobots = 1;
        long clayRobots = 0;
        long obsidianRobots = 0;
        long geodeRobots = 0;
        boolean makeOreRobot = false;
        boolean makeClayRobot = false;
        boolean makeObsidianRobot = false;
        boolean makeGeodeRobot = false;
        for (int i = 0; i < 24; i++) {
            if (ore >= blueprint.geodeRobotCostOre && obsidian >= blueprint.geodeRobotCostOreObsidian) {
                ore -= blueprint.geodeRobotCostOre;
                obsidian -= blueprint.geodeRobotCostOreObsidian;
                makeGeodeRobot = true;
            }
            if (ore >= blueprint.obsidianRobotCostOre && clay >= blueprint.obsidianRobotCostClay && obsidianRobots <= maxObsidianRobots) {
                ore -= blueprint.obsidianRobotCostOre;
                clay -= blueprint.obsidianRobotCostClay;
                makeObsidianRobot = true;
            }
            if (ore >= blueprint.clayRobotCostOre && clayRobots <= maxClayRobots) {
                ore -= blueprint.clayRobotCostOre;
                makeClayRobot = true;
            }
            if (ore >= blueprint.oreRobotCostOre && oreRobots <= maxOreRobots) {
                ore -= blueprint.oreRobotCostOre;
                makeOreRobot = true;
            }
            ore += oreRobots;
            clay += clayRobots;
            obsidian += obsidianRobots;
            geode += geodeRobots;
            if (makeGeodeRobot) {
                geodeRobots++;
                makeGeodeRobot = false;
            }
            if (makeObsidianRobot) {
                obsidianRobots++;
                makeObsidianRobot = false;
            }
            if (makeClayRobot) {
                clayRobots++;
                makeClayRobot = false;
            }
            if (makeOreRobot) {
                oreRobots++;
                makeOreRobot = false;
            }
        }
        //log("%d\tOre robots: %d | Clay robots: %d | Obsidian robots: %d | Geode robots: %d |\t Ore: %d | Clay: %d | Obsidian: %d | Geode: %d".formatted(blueprint.id, oreRobots, clayRobots, obsidianRobots, geodeRobots, ore, clay, obsidian, geode));
        return blueprint.id * geode;
    }

    @Override
    public String calculateSecondStar() {
        return "test";
    }

    private record Blueprint(int id, int oreRobotCostOre, int clayRobotCostOre, int obsidianRobotCostOre,
                             int obsidianRobotCostClay, int geodeRobotCostOre, int geodeRobotCostOreObsidian) {
    }
}
