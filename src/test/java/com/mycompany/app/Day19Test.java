package com.mycompany.app;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class Day19Test {

    @Test
    public void calculateFirstStarTestData() throws IOException {
        // given
        Day19 sut = new Day19("day-19-test");

        // when
        String result = sut.calculateFirstStar();

        // then
        assertEquals("33", result);
    }

    @Test
    public void calculateFirstStarRealData() throws IOException {
        // given
        Day19 sut = new Day19("day-19");

        // when
        String result = sut.calculateFirstStar();

        // then
        assertEquals("1624", result);
    }

    @Test
    public void calculateSecondStarTestData() throws IOException {
        // given
        Day19 sut = new Day19("day-19-test");

        // when
        String result = sut.calculateSecondStar();

        // then
        assertEquals("3472", result);
    }

    @Test
    public void calculateSecondStarRealData() throws IOException {
        // given
        Day19 sut = new Day19("day-19");

        // when
        String result = sut.calculateSecondStar();

        // then
        assertEquals("", result);
    }
}
