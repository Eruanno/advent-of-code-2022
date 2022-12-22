package com.mycompany.app;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class Day20Test {

    @Test
    public void calculateFirstStarTestData() throws IOException {
        // given
        Day20 sut = new Day20("day-20-test");

        // when
        String result = sut.calculateFirstStar();

        // then
        assertEquals("3", result);
    }

    @Test
    public void calculateFirstStarRealData() throws IOException {
        // given
        Day20 sut = new Day20("day-20");

        // when
        String result = sut.calculateFirstStar();

        // then
        assertEquals("9687", result);
    }

    @Test
    public void calculateSecondStarTestData() throws IOException {
        // given
        Day20 sut = new Day20("day-20-test");

        // when
        String result = sut.calculateSecondStar();

        // then
        assertEquals("1623178306", result);
    }

    @Test
    public void calculateSecondStarRealData() throws IOException {
        // given
        Day20 sut = new Day20("day-20");

        // when
        String result = sut.calculateSecondStar();

        // then
        assertEquals("1338310513297", result);
    }
}
