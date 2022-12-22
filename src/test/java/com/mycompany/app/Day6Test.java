package com.mycompany.app;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class Day6Test {

    @Test
    public void calculateFirstStarTestData() throws IOException {
        // given
        Day6 sut = new Day6("day-6-test-1");

        // when
        String result = sut.calculateFirstStar();

        // then
        assertEquals("7", result);
    }

    @Test
    public void calculateFirstStarRealData() throws IOException {
        // given
        Day6 sut = new Day6("day-6");

        // when
        String result = sut.calculateFirstStar();

        // then
        assertEquals("1100", result);
    }

    @Test
    public void calculateSecondStarTestData() throws IOException {
        // given
        Day6 sut = new Day6("day-6-test-1");

        // when
        String result = sut.calculateSecondStar();

        // then
        assertEquals("19", result);
    }

    @Test
    public void calculateSecondStarRealData() throws IOException {
        // given
        Day6 sut = new Day6("day-6");

        // when
        String result = sut.calculateSecondStar();

        // then
        assertEquals("2421", result);
    }
}
