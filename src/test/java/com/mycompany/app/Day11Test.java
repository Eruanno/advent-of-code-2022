package com.mycompany.app;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class Day11Test {

    @Test
    public void calculateFirstStarTestData() throws IOException {
        // given
        Day11 sut = new Day11("day-11-test");

        // when
        String result = sut.calculateFirstStar();

        // then
        assertEquals("10605", result);
    }

    @Test
    public void calculateFirstStarRealData() throws IOException {
        // given
        Day11 sut = new Day11("day-11");

        // when
        String result = sut.calculateFirstStar();

        // then
        assertEquals("54036", result);
    }

    @Test
    public void calculateSecondStarTestData() throws IOException {
        // given
        Day11 sut = new Day11("day-11-test");

        // when
        String result = sut.calculateSecondStar();

        // then
        assertEquals("2713310158", result);
    }

    @Test
    public void calculateSecondStarRealData() throws IOException {
        // given
        Day11 sut = new Day11("day-11");

        // when
        String result = sut.calculateSecondStar();

        // then
        assertEquals("13237873355", result);
    }
}
