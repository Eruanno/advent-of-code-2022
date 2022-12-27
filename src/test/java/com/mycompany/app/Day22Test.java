package com.mycompany.app;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class Day22Test {

    @Test
    public void calculateFirstStarTestData() throws IOException {
        // given
        Day sut = new Day22("day-22-test", true);

        // when
        String result = sut.calculateFirstStar();

        // then
        assertEquals("6032", result);
    }

    @Test
    public void calculateFirstStarRealData() throws IOException {
        // given
        Day sut = new Day22("day-22", false);

        // when
        String result = sut.calculateFirstStar();

        // then
        assertEquals("65368", result);
    }

    @Test
    public void calculateSecondStarTestData() throws IOException {
        // given
        Day sut = new Day22("day-22-test", true);

        // when
        String result = sut.calculateSecondStar();

        // then
        assertEquals("5031", result);
    }

    @Test
    public void calculateSecondStarRealData() throws IOException {
        // given
        Day sut = new Day22("day-22", false);

        // when
        String result = sut.calculateSecondStar();

        // then
        assertEquals("190117", result);
    }
}
