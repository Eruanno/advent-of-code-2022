package com.mycompany.app;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class Day14Test {

    @Test
    public void calculateFirstStarTestData() throws IOException {
        // given
        Day14 sut = new Day14("day-14-test");

        // when
        String result = sut.calculateFirstStar();

        // then
        assertEquals("24", result);
    }

    @Test
    public void calculateFirstStarRealData() throws IOException {
        // given
        Day14 sut = new Day14("day-14");

        // when
        String result = sut.calculateFirstStar();

        // then
        assertEquals("901", result);
    }

    @Test
    public void calculateSecondStarTestData() throws IOException {
        // given
        Day14 sut = new Day14("day-14-test");

        // when
        String result = sut.calculateSecondStar();

        // then
        assertEquals("93", result);
    }

    @Test
    public void calculateSecondStarRealData() throws IOException {
        // given
        Day14 sut = new Day14("day-14");

        // when
        String result = sut.calculateSecondStar();

        // then
        assertEquals("24589", result);
    }
}
