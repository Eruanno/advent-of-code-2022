package com.mycompany.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.mycompany.app.FileReader.readInput;
import static java.lang.Integer.parseInt;
import static java.lang.Math.max;

public class Day18 implements Day {
    private final List<Scan> scanned = new ArrayList<>();
    private int[][][] droplet;
    private int dimension = 0;

    private final String filename;
    private List<String> input;

    public Day18(String filename) {
        this.filename = filename;
    }

    @Override
    public void loadData() throws IOException {
        input = readInput(filename);
        prepareData();
    }

    private void prepareData() {
        for (String line : input) {
            String[] coordinates = line.split(",");
            scanned.add(new Scan(parseInt(coordinates[0]), parseInt(coordinates[1]), parseInt(coordinates[2])));
        }
        dimension = scanned.stream().map(s -> max(max(s.x, s.y), s.z)).max(Integer::compareTo).get() + 2;
        droplet = new int[dimension][][];
        for (int i = 0; i < dimension; i++) {
            droplet[i] = new int[dimension][];
            for (int j = 0; j < dimension; j++) {
                droplet[i][j] = new int[dimension];
                for (int k = 0; k < dimension; k++) {
                    droplet[i][j][k] = 0;
                }
            }
        }
        for (Scan scan : scanned) {
            droplet[scan.x][scan.y][scan.z] = 1;
        }
    }

    @Override
    public String calculateFirstStar() {
        long acc = 0;
        for (int x = 0; x < dimension; x++) {
            for (int y = 0; y < dimension; y++) {
                for (int z = 0; z < dimension; z++) {
                    if (droplet[x][y][z] == 1) {
                        acc += check(x, y, z, 0);
                    }
                }
            }
        }
        return "" + acc;
    }

    /**
     * stat == 0 empty
     * state == 1 lava
     * state == 2 outer air
     */
    private long check(int x, int y, int z, int state) {
        long acc = 0;
        for (int xx = x - 1; xx <= x + 1; xx++) {
            if (xx >= 0 && xx <= dimension) {
                if (droplet[xx][y][z] == state) {
                    acc++;
                }
            } else {
                acc++;
            }
        }
        for (int yy = y - 1; yy <= y + 1; yy++) {
            if (yy >= 0 && yy <= dimension) {
                if (droplet[x][yy][z] == state) {
                    acc++;
                }
            } else {
                acc++;
            }
        }
        for (int zz = z - 1; zz <= z + 1; zz++) {
            if (zz >= 0 && zz <= dimension) {
                if (droplet[x][y][zz] == state) {
                    acc++;
                }
            } else {
                acc++;
            }
        }
        return acc;
    }

    @Override
    public String calculateSecondStar() {
        // Mark bottom
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (droplet[i][j][0] == 0) {
                    droplet[i][j][0] = 2;
                }
            }
        }
        // Mark top
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (droplet[i][j][dimension - 1] == 0) {
                    droplet[i][j][dimension - 1] = 2;
                }
            }
        }
        // Mark sides
        for (int k = 0; k < dimension; k++) {
            for (int i = 0; i < dimension; i++) {
                if (droplet[dimension - 1][i][k] == 0) {
                    droplet[dimension - 1][i][k] = 2;
                }
                if (droplet[i][0][k] == 0) {
                    droplet[i][0][k] = 2;
                }
                if (droplet[0][i][k] == 0) {
                    droplet[0][i][k] = 2;
                }
                if (droplet[i][dimension - 1][k] == 0) {
                    droplet[i][dimension - 1][k] = 2;
                }
            }
        }
        // Mark exterior air
        for (int x = 0; x < dimension; x++) {
            for (int y = 0; y < dimension; y++) {
                for (int z = 0; z < dimension; z++) {
                    if (droplet[x][y][z] == 0 && adjAir(x, y, z)) {
                        droplet[x][y][z] = 2;
                    }
                }
            }
        }
        for (int x = dimension - 1; x >= 0; x--) {
            for (int y = dimension - 1; y >= 0; y--) {
                for (int z = dimension - 1; z >= 0; z--) {
                    if (droplet[x][y][z] == 0 && adjAir(x, y, z)) {
                        droplet[x][y][z] = 2;
                    }
                }
            }
        }
        // Count exterior surface
        long acc = 0;
        for (int x = 0; x < dimension; x++) {
            for (int y = 0; y < dimension; y++) {
                for (int z = 0; z < dimension; z++) {
                    if (droplet[x][y][z] == 1) {
                        acc += check(x, y, z, 2);
                    }
                }
            }
        }
        return "" + acc;
    }

    private boolean adjAir(int x, int y, int z) {
        for (int xx = x - 1; xx <= x + 1; xx++) {
            if (xx >= 0 && xx <= dimension) {
                if (droplet[xx][y][z] == 2) {
                    return true;
                }
            } else {
                return true;
            }
        }
        for (int yy = y - 1; yy <= y + 1; yy++) {
            if (yy >= 0 && yy <= dimension) {
                if (droplet[x][yy][z] == 2) {
                    return true;
                }
            } else {
                return true;
            }
        }
        for (int zz = z - 1; zz <= z + 1; zz++) {
            if (zz >= 0 && zz <= dimension) {
                if (droplet[x][y][zz] == 2) {
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;
    }

    private record Scan(int x, int y, int z) {
    }
}
