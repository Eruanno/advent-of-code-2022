package com.mycompany.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mycompany.app.FileReader.readInput;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class Day23 implements Day {
    private final Map<Point, List<Elf>> nextMoveProposals = new HashMap<>();
    private final List<Elf> elves = new ArrayList<>();
    private final String filename;
    private List<String> input;
    private int order = 0;
    private int left = Integer.MAX_VALUE;
    private int right = Integer.MIN_VALUE;
    private int top = Integer.MAX_VALUE;
    private int bottom = Integer.MIN_VALUE;
    private int width = 0;
    private int height = 0;

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
                if (line.charAt(j) == '#') {
                    elves.add(new Elf(new Point(j, i), null));
                }
            }
        }
    }

    @Override
    public String calculateFirstStar() {
        for (int i = 0; i < 10; i++) {
            iterate();
        }
        return "" + sumEmptySpaces();
    }

    private int sumEmptySpaces() {
        recalculateDimensions();
        int acc = 0;
        for (int y = 0; y <= height; y++) {
            for (int x = 0; x <= width; x++) {
                boolean empty = true;
                for (Elf elf : elves) {
                    if (elf.position.x == x + left && elf.position.y == y + top) {
                        empty = false;
                        break;
                    }
                }
                if (empty) {
                    acc++;
                }
            }
        }
        return acc;
    }

    private void recalculateDimensions() {
        left = Integer.MAX_VALUE;
        right = Integer.MIN_VALUE;
        top = Integer.MAX_VALUE;
        bottom = Integer.MIN_VALUE;
        for (Elf elf : elves) {
            left = min(left, elf.position.x);
            right = max(right, elf.position.x);
            top = min(top, elf.position.y);
            bottom = max(bottom, elf.position.y);
        }
        width = right - left;
        height = bottom - top;
    }

    @Override
    public String calculateSecondStar() {
        int i = 0;
        do {
            iterate();
            i++;
        } while (!nextMoveProposals.isEmpty());
        return "" + i;
    }

    private void iterate() {
        nextMoveProposals.clear();
        for (Elf elf : elves) {
            if (hasToMove(elf)) {
                Point nextMove = null;
                if (order == 0) {
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
                } else if (order == 1) {
                    if (isSouthEmpty(elf)) {
                        nextMove = new Point(elf.position.x, elf.position.y + 1);
                    } else if (isWestEmpty(elf)) {
                        nextMove = new Point(elf.position.x - 1, elf.position.y);
                    } else if (isEastEmpty(elf)) {
                        nextMove = new Point(elf.position.x + 1, elf.position.y);
                    } else if (isNorthEmpty(elf)) {
                        nextMove = new Point(elf.position.x, elf.position.y - 1);
                    } else {
                        nextMove = elf.position;
                    }
                } else if (order == 2) {
                    if (isWestEmpty(elf)) {
                        nextMove = new Point(elf.position.x - 1, elf.position.y);
                    } else if (isEastEmpty(elf)) {
                        nextMove = new Point(elf.position.x + 1, elf.position.y);
                    } else if (isNorthEmpty(elf)) {
                        nextMove = new Point(elf.position.x, elf.position.y - 1);
                    } else if (isSouthEmpty(elf)) {
                        nextMove = new Point(elf.position.x, elf.position.y + 1);
                    } else {
                        nextMove = elf.position;
                    }
                } else if (order == 3) {
                    if (isEastEmpty(elf)) {
                        nextMove = new Point(elf.position.x + 1, elf.position.y);
                    } else if (isNorthEmpty(elf)) {
                        nextMove = new Point(elf.position.x, elf.position.y - 1);
                    } else if (isSouthEmpty(elf)) {
                        nextMove = new Point(elf.position.x, elf.position.y + 1);
                    } else if (isWestEmpty(elf)) {
                        nextMove = new Point(elf.position.x - 1, elf.position.y);
                    } else {
                        nextMove = elf.position;
                    }
                }
                List<Elf> evs;
                if (nextMoveProposals.containsKey(nextMove)) {
                    evs = nextMoveProposals.get(nextMove);
                } else {
                    evs = new ArrayList<>();
                }
                evs.add(new Elf(elf.position, nextMove));
                nextMoveProposals.put(nextMove, evs);
            }
        }
        for (Map.Entry<Point, List<Elf>> entry : nextMoveProposals.entrySet()) {
            if (entry.getValue().size() == 1) {
                elves.remove(new Elf(entry.getValue().get(0).position, null));
                elves.add(new Elf(entry.getValue().get(0).nextMove, null));
            }
        }
        order = (order + 1) % 4;
    }

    private boolean hasToMove(Elf elf) {
        Point position = elf.position;
        for (Elf other : elves) {
            if (other.position.x == position.x - 1 && other.position.y == position.y - 1) {
                return true;
            }
            if (other.position.x == position.x && other.position.y == position.y - 1) {
                return true;
            }
            if (other.position.x == position.x + 1 && other.position.y == position.y - 1) {
                return true;
            }
            if (other.position.x == position.x - 1 && other.position.y == position.y) {
                return true;
            }
            if (other.position.x == position.x + 1 && other.position.y == position.y) {
                return true;
            }
            if (other.position.x == position.x - 1 && other.position.y == position.y + 1) {
                return true;
            }
            if (other.position.x == position.x && other.position.y == position.y + 1) {
                return true;
            }
            if (other.position.x == position.x + 1 && other.position.y == position.y + 1) {
                return true;
            }
        }
        return false;
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

    private record Point(int x, int y) {
    }

    private record Elf(Point position, Point nextMove) {
    }
}
