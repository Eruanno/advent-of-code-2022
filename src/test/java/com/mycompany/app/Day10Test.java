package com.mycompany.app;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class Day10Test {

    @Test
    public void calculateFirstStarTestData() throws IOException {
        // given
        Day10 sut = new Day10("day-10-test");

        // when
        String result = sut.calculateFirstStar();

        // then
        assertEquals("13140", result);
    }

    @Test
    public void calculateFirstStarRealData() throws IOException {
        // given
        Day10 sut = new Day10("day-10");

        // when
        String result = sut.calculateFirstStar();

        // then
        assertEquals("11780", result);
    }

    @Test
    public void calculateSecondStarTestData() throws IOException {
        // given
        Day10 sut = new Day10("day-10-test");

        // when
        String result = sut.calculateSecondStar();

        // then
        assertEquals("##..##..##..##..##..##..##..##..##..##..\n" +
                "###...###...###...###...###...###...###.\n" +
                "####....####....####....####....####....\n" +
                "#####.....#####.....#####.....#####.....\n" +
                "######......######......######......####\n" +
                "#######.......#######.......#######.....\n", result);
    }

    @Test
    public void calculateSecondStarRealData() throws IOException {
        // given
        Day10 sut = new Day10("day-10");

        // when
        String result = sut.calculateSecondStar();

        // then
        assertEquals("###..####.#..#.#....###...##..#..#..##..\n" +
                "#..#....#.#..#.#....#..#.#..#.#..#.#..#.\n" +
                "#..#...#..#..#.#....###..#..#.#..#.#..#.\n" +
                "###...#...#..#.#....#..#.####.#..#.####.\n" +
                "#....#....#..#.#....#..#.#..#.#..#.#..#.\n" +
                "#....####..##..####.###..#..#..##..#..#.\n", result);
    }
}
