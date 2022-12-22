package com.mycompany.app;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class Day13Test {

    @Test
    public void calculateFirstStarTestData() throws IOException {
        // given
        Day13 sut = new Day13("day-13-test");

        // when
        String result = sut.calculateFirstStar();

        // then
        assertEquals("13", result);
    }

    @Test
    public void calculateFirstStarRealData() throws IOException {
        // given
        Day13 sut = new Day13("day-13");

        // when
        String result = sut.calculateFirstStar();

        // then
        assertEquals("6369", result);
    }

    @Test
    public void calculateSecondStarTestData() throws IOException {
        // given
        Day13 sut = new Day13("day-13-test");

        // when
        String result = sut.calculateSecondStar();

        // then
        assertEquals("140", result);
    }

    @Test
    public void calculateSecondStarRealData() throws IOException {
        // given
        Day13 sut = new Day13("day-13");

        // when
        String result = sut.calculateSecondStar();

        // then
        assertEquals("25800", result);
    }
}
