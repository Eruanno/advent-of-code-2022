package com.mycompany.app;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class Day2Test {

    @Test
    public void calculateFirstStarTestData() throws IOException {
        // given
        Day2 sut = new Day2("day-2-test");

        // when
        String result = sut.calculateFirstStar();

        // then
        assertEquals("15", result);
    }

    @Test
    public void calculateFirstStarRealData() throws IOException {
        // given
        Day2 sut = new Day2("day-2");

        // when
        String result = sut.calculateFirstStar();

        // then
        assertEquals("13268", result);
    }

    @Test
    public void calculateSecondStarTestData() throws IOException {
        // given
        Day2 sut = new Day2("day-2-test");

        // when
        String result = sut.calculateSecondStar();

        // then
        assertEquals("12", result);
    }

    @Test
    public void calculateSecondStarRealData() throws IOException {
        // given
        Day2 sut = new Day2("day-2");

        // when
        String result = sut.calculateSecondStar();

        // then
        assertEquals("15508", result);
    }
}
