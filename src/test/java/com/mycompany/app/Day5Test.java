package com.mycompany.app;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class Day5Test {

    @Test
    public void calculateFirstStarTestData() throws IOException {
        // given
        Day5 sut = new Day5("day-5-test", true);

        // when
        String result = sut.calculateFirstStar();

        // then
        assertEquals("CMZ", result);
    }

    @Test
    public void calculateFirstStarRealData() throws IOException {
        // given
        Day5 sut = new Day5("day-5", false);

        // when
        String result = sut.calculateFirstStar();

        // then
        assertEquals("VJSFHWGFT", result);
    }

    @Test
    public void calculateSecondStarTestData() throws IOException {
        // given
        Day5 sut = new Day5("day-5-test", true);

        // when
        String result = sut.calculateSecondStar();

        // then
        assertEquals("MCD", result);
    }

    @Test
    public void calculateSecondStarRealData() throws IOException {
        // given
        Day5 sut = new Day5("day-5", false);

        // when
        String result = sut.calculateSecondStar();

        // then
        assertEquals("LCTQFBVZV", result);
    }
}
