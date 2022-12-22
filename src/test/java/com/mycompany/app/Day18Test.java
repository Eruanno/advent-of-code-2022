package com.mycompany.app;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class Day18Test {

    @Test
    public void calculateFirstStarTestData() throws IOException {
        // given
        Day18 sut = new Day18("day-18-test");

        // when
        String result = sut.calculateFirstStar();

        // then
        assertEquals("64", result);
    }

    @Test
    public void calculateFirstStarRealData() throws IOException {
        // given
        Day18 sut = new Day18("day-18");

        // when
        String result = sut.calculateFirstStar();

        // then
        assertEquals("4504", result);
    }

    @Test
    public void calculateSecondStarTestData() throws IOException {
        // given
        Day18 sut = new Day18("day-18-test");

        // when
        String result = sut.calculateSecondStar();

        // then
        assertEquals("58", result);
    }

    @Test
    public void calculateSecondStarRealData() throws IOException {
        // given
        Day18 sut = new Day18("day-18");

        // when
        String result = sut.calculateSecondStar();

        // then
        assertEquals("2556", result);
    }
}
