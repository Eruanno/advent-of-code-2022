package com.mycompany.app;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class Day25Test {

    @Test
    public void calculateFirstStarTestData() throws IOException {
        // given
        Day sut = new Day25("day-25-test");

        // when
        String result = sut.calculateFirstStar();

        // then
        assertEquals("", result);
    }

    @Test
    public void calculateFirstStarRealData() throws IOException {
        // given
        Day sut = new Day25("day-25");

        // when
        String result = sut.calculateFirstStar();

        // then
        assertEquals("", result);
    }

    @Test
    public void calculateSecondStarTestData() throws IOException {
        // given
        Day sut = new Day25("day-25-test");

        // when
        String result = sut.calculateSecondStar();

        // then
        assertEquals("", result);
    }

    @Test
    public void calculateSecondStarRealData() throws IOException {
        // given
        Day sut = new Day25("day-25");

        // when
        String result = sut.calculateSecondStar();

        // then
        assertEquals("", result);
    }
}
