package com.mycompany.app;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class Day7Test {

    @Test
    public void calculateFirstStarTestData() throws IOException {
        // given
        Day7 sut = new Day7("day-7-test");

        // when
        String result = sut.calculateFirstStar();

        // then
        assertEquals("95437", result);
    }

    @Test
    public void calculateFirstStarRealData() throws IOException {
        // given
        Day7 sut = new Day7("day-7");

        // when
        String result = sut.calculateFirstStar();

        // then
        assertEquals("1449447", result);
    }

    @Test
    public void calculateSecondStarTestData() throws IOException {
        // given
        Day7 sut = new Day7("day-7-test");

        // when
        String result = sut.calculateSecondStar();

        // then
        assertEquals("24933642", result);
    }

    @Test
    public void calculateSecondStarRealData() throws IOException {
        // given
        Day7 sut = new Day7("day-7");

        // when
        String result = sut.calculateSecondStar();

        // then
        assertEquals("8679207", result);
    }
}
