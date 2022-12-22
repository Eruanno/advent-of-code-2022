package com.mycompany.app;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class Day9Test {

    @Test
    public void calculateFirstStarTestData() throws IOException {
        // given
        Day9 sut = new Day9("day-9-test");

        // when
        String result = sut.calculateFirstStar();

        // then
        assertEquals("13", result);
    }

    @Test
    public void calculateFirstStarRealData() throws IOException {
        // given
        Day9 sut = new Day9("day-9");

        // when
        String result = sut.calculateFirstStar();

        // then
        assertEquals("5874", result);
    }

    @Test
    public void calculateSecondStarTestData() throws IOException {
        // given
        Day9 sut = new Day9("day-9-test");

        // when
        String result = sut.calculateSecondStar();

        // then
        assertEquals("1", result);
    }

    @Test
    public void calculateSecondStarRealData() throws IOException {
        // given
        Day9 sut = new Day9("day-9");

        // when
        String result = sut.calculateSecondStar();

        // then
        assertEquals("2467", result);
    }
}
