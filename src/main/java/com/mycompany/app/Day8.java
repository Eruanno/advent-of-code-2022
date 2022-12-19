package com.mycompany.app;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.mycompany.app.FileReader.readInput;
import static com.mycompany.app.Logger.log;

class Day8 implements Day {

    boolean[][] visibilityMap;
    long[][] heightMap;
    long[][] scenicMap;
    int height;
    int width;

    public void solve() throws IOException {
        List<String> input = readInput("day-8");
        log("Day 8:");
        prepareData(input);
        log("First star:");
        log(calculateFirstStar());
        log("Second star:");
        log(calculateSecondStar());
    }

    void prepareData(List<String> input) {
        height = input.size();
        width = input.get(0).length();

        visibilityMap = new boolean[width][height];
        heightMap = new long[width][height];
        scenicMap = new long[width][height];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                heightMap[i][j] = Integer.parseInt(input.get(i).substring(j, j + 1));
                visibilityMap[i][j] = true;
                scenicMap[i][j] = 0;
            }
        }
    }

    Long calculateFirstStar() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                visibilityMap[i][j] = checkVisibility(i, j);
            }
        }
        long acc = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (visibilityMap[i][j])
                    acc++;
            }
        }
        return acc;
    }

    private boolean checkVisibility(int i, int j) {
        if (i == 0 || j == 0 || i == height - 1 || j == width - 1) {
            return true;
        }
        long height = heightMap[i][j];
        boolean eVisible = true;
        for (int e = 0; e < i; e++) {
            if (heightMap[e][j] >= height) {
                eVisible = false;
                break;
            }
        }
        boolean wVisible = true;
        for (int w = i + 1; w < width; w++) {
            if (heightMap[w][j] >= height) {
                wVisible = false;
                break;
            }
        }
        boolean nVisible = true;
        for (int n = 0; n < j; n++) {
            if (heightMap[i][n] >= height) {
                nVisible = false;
                break;
            }
        }
        boolean sVisible = true;
        for (int s = j + 1; s < width; s++) {
            if (heightMap[i][s] >= height) {
                sVisible = false;
                break;
            }
        }
        return eVisible || wVisible || nVisible || sVisible;
    }

    Long calculateSecondStar() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                scenicMap[i][j] = calculateScenicScore(i, j);
            }
        }
        return Arrays.stream(scenicMap)
                     .flatMapToLong(Arrays::stream)
                     .max().orElse(Long.MAX_VALUE);
    }

    private int calculateScenicScore(int i, int j) {
        long height = heightMap[i][j];
        int eScore = 0;
        if (j > 0) {
            for (int e = j - 1; e >= 0; e--) {
                eScore++;
                if (heightMap[i][e] >= height) {
                    break;
                }
            }
        }
        int wScore = 0;
        if (j < width) {
            for (int w = j + 1; w < width; w++) {
                wScore++;
                if (heightMap[i][w] >= height) {
                    break;
                }
            }
        }
        int nScore = 0;
        if (i > 0) {
            for (int n = i - 1; n >= 0; n--) {
                nScore++;
                if (heightMap[n][j] >= height) {
                    break;
                }
            }
        }
        int sScore = 0;
        if (i < width) {
            for (int s = i + 1; s < width; s++) {
                sScore++;
                if (heightMap[s][j] >= height) {
                    break;
                }
            }
        }
        return eScore * wScore * nScore * sScore;
    }
}
