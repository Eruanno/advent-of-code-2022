package com.mycompany.app;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class Day17Test {

    @Test
    public void calculateFirstStarTestData() throws IOException {
        // given
        Day17 sut = new Day17("day-17-test");

        // when
        String result = sut.calculateFirstStar();

        // then
        assertEquals("3068", result);
    }

    @Test
    public void calculateFirstStarRealData() throws IOException {
        // given
        Day17 sut = new Day17("day-17");

        // when
        String result = sut.calculateFirstStar();

        // then
        assertEquals("3067", result);
    }

    @Test
    public void calculateSecondStarTestData() throws IOException {
        // given
        Day17 sut = new Day17("day-17-test");

        // when
        String result = sut.calculateSecondStar();

        // then
        assertEquals("1514285714288", result);
    }

    @Test
    public void calculateSecondStarRealData() throws IOException {
        // given
        Day17 sut = new Day17("day-17");

        // when
        String result = sut.calculateSecondStar();

        // then
        assertEquals("1514369501484", result);
    }
}
