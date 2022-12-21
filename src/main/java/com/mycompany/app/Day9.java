package com.mycompany.app;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.mycompany.app.FileReader.readInput;

public class Day9 implements Day {

    private List<Move> moves;

    private final String filename;
    private List<String> input;

    public Day9(String filename) {
        this.filename = filename;
    }

    @Override
    public void loadData() throws IOException {
        input = readInput(filename);
    }

    private void prepareData(List<String> input) {
        moves = input.stream().map(Move::new).toList();
    }

    @Override
    public String calculateFirstStar() {
        return calculateTailUniquePositions(2);
    }

    @Override
    public String calculateSecondStar() {
        return calculateTailUniquePositions(10);
    }

    private String calculateTailUniquePositions(int length) {
        MutablePosition[] rope = new MutablePosition[length];
        for (int i = 0; i < rope.length; i++) {
            rope[i] = new MutablePosition();
        }
        Set<Position> visited = new HashSet<>();
        visited.add(new Position(rope[length - 1].x, rope[length - 1].y));
        for (Move move : moves) {
            for (int i = 0; i < move.distance; i++) {
                // Move head.
                if ("R".equals(move.direction)) {
                    rope[0].x++;
                } else if ("L".equals(move.direction)) {
                    rope[0].x--;
                } else if ("U".equals(move.direction)) {
                    rope[0].y++;
                } else if ("D".equals(move.direction)) {
                    rope[0].y--;
                }
                // Move rest of the rope.
                for (int j = 1; j < rope.length; j++) {
                    if (calculateDistance(rope[j - 1], rope[j]) > 1) {
                        if (rope[j - 1].x == rope[j].x) {
                            rope[j].y += (rope[j - 1].y - rope[j].y) / 2;
                        } else if (rope[j - 1].y == rope[j].y) {
                            rope[j].x += (rope[j - 1].x - rope[j].x) / 2;
                        } else {
                            if (rope[j - 1].y > rope[j].y) {
                                rope[j].y++;
                            } else if (rope[j - 1].y < rope[j].y) {
                                rope[j].y--;
                            }
                            if (rope[j - 1].x > rope[j].x) {
                                rope[j].x++;
                            } else if (rope[j - 1].x < rope[j].x) {
                                rope[j].x--;
                            }
                        }
                    }
                }
                // Save tail position.
                visited.add(new Position(rope[length - 1].x, rope[length - 1].y));
            }
        }
        return "" + visited.size();
    }

    private int calculateDistance(MutablePosition positionA, MutablePosition positionB) {
        int x = Math.abs(positionA.x - positionB.x);
        int y = Math.abs(positionA.y - positionB.y);
        return Math.max(x, y);
    }

    private record Position(int x, int y) {
    }

    private static class Move {
        private final String direction;
        private final int distance;

        Move(String move) {
            String[] parts = move.split(" ");
            direction = parts[0];
            distance = Integer.parseInt(parts[1]);
        }
    }

    private static class MutablePosition {
        private int x = 0;
        private int y = 0;

        public String toString() {
            return "[%d, %d]".formatted(x, y);
        }
    }
}
