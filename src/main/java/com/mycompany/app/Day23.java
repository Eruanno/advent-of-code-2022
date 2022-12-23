package com.mycompany.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mycompany.app.FileReader.readInput;
import static java.lang.Math.*;

public class Day23 implements Day {
    private final List<Elf> elves = new ArrayList<>();
    private final String filename;
    private List<String> input;
    private int left = Integer.MAX_VALUE;
    private int right = Integer.MIN_VALUE;
    private int top = Integer.MAX_VALUE;
    private int bottom = Integer.MIN_VALUE;

    public Day23(String filename) throws IOException {
        this.filename = filename;
        loadData();
    }

    private void loadData() throws IOException {
        input = readInput(filename);
        prepareData();
    }

    private void prepareData() {
        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            for (int j = 0; j < line.length(); j++) {
                if (line.charAt(i) == '#') {
                    elves.add(new Elf(new Point(j, i), null));
                    left = min(left, j);
                    right = max(right, j);
                    top = min(top, j);
                    bottom = max(bottom, j);
                }
            }
        }
    }

    @Override
    public String calculateFirstStar() {
        Map<Point, List<Elf>> propositions = new HashMap<>();
        for (Elf elf : elves) {
            Point nextMove;
            if (isNorthEmpty(elf)) {
                nextMove = new Point(elf.position.x, elf.position.y - 1);
            } else if (isSouthEmpty(elf)) {
                nextMove = new Point(elf.position.x, elf.position.y + 1);
            } else if (isWestEmpty(elf)) {
                nextMove = new Point(elf.position.x - 1, elf.position.y);
            } else if (isEastEmpty(elf)) {
                nextMove = new Point(elf.position.x + 1, elf.position.y);
            } else {
                nextMove = elf.position;
            }
            List<Elf> evs;
            if (propositions.containsKey(nextMove)) {
                evs = propositions.get(nextMove);
            } else {
                evs = new ArrayList<>();
            }
            evs.add(new Elf(elf.position, nextMove));
            propositions.put(nextMove, evs);
        }


        int width = right - left;
        int height = bottom - top;
        return "" + (abs(width * height) - elves.size());
    }

    private boolean isNorthEmpty(Elf elf) {
        Point position = elf.position;
        for (Elf other : elves) {
            if (other.position.x == position.x && other.position.y == position.y - 1) {
                return false;
            }
            if (other.position.x == position.x + 1 && other.position.y == position.y - 1) {
                return false;
            }
            if (other.position.x == position.x - 1 && other.position.y == position.y - 1) {
                return false;
            }
        }
        return true;
    }

    private boolean isSouthEmpty(Elf elf) {
        Point position = elf.position;
        for (Elf other : elves) {
            if (other.position.x == position.x && other.position.y == position.y + 1) {
                return false;
            }
            if (other.position.x == position.x + 1 && other.position.y == position.y + 1) {
                return false;
            }
            if (other.position.x == position.x - 1 && other.position.y == position.y + 1) {
                return false;
            }
        }
        return true;
    }

    private boolean isWestEmpty(Elf elf) {
        Point position = elf.position;
        for (Elf other : elves) {
            if (other.position.x == position.x - 1 && other.position.y == position.y) {
                return false;
            }
            if (other.position.x == position.x - 1 && other.position.y == position.y + 1) {
                return false;
            }
            if (other.position.x == position.x - 1 && other.position.y == position.y - 1) {
                return false;
            }
        }
        return true;
    }

    private boolean isEastEmpty(Elf elf) {
        Point position = elf.position;
        for (Elf other : elves) {
            if (other.position.x == position.x + 1 && other.position.y == position.y) {
                return false;
            }
            if (other.position.x == position.x + 1 && other.position.y == position.y + 1) {
                return false;
            }
            if (other.position.x == position.x + 1 && other.position.y == position.y - 1) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String calculateSecondStar() {
        return null;
    }

    private record Point(int x, int y) {
    }

    private record Elf(Point position, Point nextMove) {
    }
}
