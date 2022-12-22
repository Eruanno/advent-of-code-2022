package com.mycompany.app;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class Day4Test {

    @Test
    public void calculateFirstStarTestData() throws IOException {
        // given
        Day4 sut = new Day4("day-4-test");

        // when
        String result = sut.calculateFirstStar();

        // then
        assertEquals("2", result);
    }

    @Test
    public void calculateFirstStarRealData() throws IOException {
        // given
        Day4 sut = new Day4("day-4");

        // when
        String result = sut.calculateFirstStar();

        // then
        assertEquals("550", result);
    }

    @Test
    public void calculateSecondStarTestData() throws IOException {
        // given
        Day4 sut = new Day4("day-4-test");

        // when
        String result = sut.calculateSecondStar();

        // then
        assertEquals("4", result);
    }

    @Test
    public void calculateSecondStarRealData() throws IOException {
        // given
        Day4 sut = new Day4("day-4");

        // when
        String result = sut.calculateSecondStar();

        // then
        assertEquals("931", result);
    }
}
