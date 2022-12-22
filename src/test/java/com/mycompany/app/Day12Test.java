package com.mycompany.app;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class Day12Test {

    @Test
    public void calculateFirstStarTestData() throws IOException {
        // given
        Day12 sut = new Day12("day-12-test");

        // when
        String result = sut.calculateFirstStar();

        // then
        assertEquals("31", result);
    }

    @Test
    public void calculateFirstStarRealData() throws IOException {
        // given
        Day12 sut = new Day12("day-12");

        // when
        String result = sut.calculateFirstStar();

        // then
        assertEquals("484", result);
    }

    @Test
    public void calculateSecondStarTestData() throws IOException {
        // given
        Day12 sut = new Day12("day-12-test");

        // when
        String result = sut.calculateSecondStar();

        // then
        assertEquals("29", result);
    }

    @Test
    public void calculateSecondStarRealData() throws IOException {
        // given
        Day12 sut = new Day12("day-12");

        // when
        String result = sut.calculateSecondStar();

        // then
        assertEquals("478", result);
    }
}
