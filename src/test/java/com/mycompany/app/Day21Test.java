package com.mycompany.app;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;


public class Day21Test {

    @Test
    public void calculateFirstStarTestData() throws IOException {
        // given
        Day sut = new Day21("day-21-test");

        // when
        String result = sut.calculateFirstStar();

        // then
        assertEquals("152", result);
    }

    @Test
    public void calculateFirstStarRealData() throws IOException {
        // given
        Day sut = new Day21("day-21");

        // when
        String result = sut.calculateFirstStar();

        // then
        assertEquals("169525884255464", result);
    }

    @Test
    public void calculateSecondStarTestData() throws IOException {
        // given
        Day sut = new Day21("day-21-test");

        // when
        String result = sut.calculateSecondStar();

        // then
        assertEquals("301", result);
    }

    @Test
    public void calculateSecondStarRealData() throws IOException {
        // given
        Day sut = new Day21("day-21");

        // when
        String result = sut.calculateSecondStar();

        // then
        assertEquals("", result);
    }
}