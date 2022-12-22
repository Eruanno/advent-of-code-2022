package com.mycompany.app;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class Day1Test {

    @Test
    public void calculateFirstStarTestData() throws IOException {
        // given
        Day1 sut = new Day1("day-1-test");

        // when
        String result = sut.calculateFirstStar();

        // then
        assertEquals("24000", result);
    }

    @Test
    public void calculateFirstStarRealData() throws IOException {
        // given
        Day1 sut = new Day1("day-1");

        // when
        String result = sut.calculateFirstStar();

        // then
        assertEquals("72602", result);
    }

    @Test
    public void calculateSecondStarTestData() throws IOException {
        // given
        Day1 sut = new Day1("day-1-test");

        // when
        String result = sut.calculateSecondStar();

        // then
        assertEquals("45000", result);
    }

    @Test
    public void calculateSecondStarRealData() throws IOException {
        // given
        Day1 sut = new Day1("day-1");

        // when
        String result = sut.calculateSecondStar();

        // then
        assertEquals("207410", result);
    }
}
