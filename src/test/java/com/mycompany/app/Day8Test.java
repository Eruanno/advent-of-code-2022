package com.mycompany.app;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class Day8Test {

    @Test
    public void calculateFirstStarTestData() throws IOException {
        // given
        Day8 sut = new Day8("day-8-test");

        // when
        String result = sut.calculateFirstStar();

        // then
        assertEquals("21", result);
    }

    @Test
    public void calculateFirstStarRealData() throws IOException {
        // given
        Day8 sut = new Day8("day-8");

        // when
        String result = sut.calculateFirstStar();

        // then
        assertEquals("1776", result);
    }

    @Test
    public void calculateSecondStarTestData() throws IOException {
        // given
        Day8 sut = new Day8("day-8-test");

        // when
        String result = sut.calculateSecondStar();

        // then
        assertEquals("8", result);
    }

    @Test
    public void calculateSecondStarRealData() throws IOException {
        // given
        Day8 sut = new Day8("day-8");

        // when
        String result = sut.calculateSecondStar();

        // then
        assertEquals("234416", result);
    }
}
