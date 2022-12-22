package com.mycompany.app;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class Day3Test {

    @Test
    public void calculateFirstStarTestData() throws IOException {
        // given
        Day3 sut = new Day3("day-3-test");

        // when
        String result = sut.calculateFirstStar();

        // then
        assertEquals("157", result);
    }

    @Test
    public void calculateFirstStarRealData() throws IOException {
        // given
        Day3 sut = new Day3("day-3");

        // when
        String result = sut.calculateFirstStar();

        // then
        assertEquals("7990", result);
    }

    @Test
    public void calculateSecondStarTestData() throws IOException {
        // given
        Day3 sut = new Day3("day-3-test");

        // when
        String result = sut.calculateSecondStar();

        // then
        assertEquals("70", result);
    }

    @Test
    public void calculateSecondStarRealData() throws IOException {
        // given
        Day3 sut = new Day3("day-3");

        // when
        String result = sut.calculateSecondStar();

        // then
        assertEquals("2602", result);
    }
}
