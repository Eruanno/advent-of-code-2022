package com.mycompany.app;


import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class Day15Test {

    @Test
    public void calculateFirstStarTestData() throws IOException {
        // given
        Day15 day = new Day15();
        day.setFilename("day-15-test");
        day.loadInput();
        day.prepareData();

        // when
        long result = day.calculateFirstStar(10);

        // then
        assertEquals(26, result);
    }

    @Test
    public void calculateFirstStarRealData() throws IOException {
        // given
        Day15 day = new Day15();
        day.setFilename("day-15");
        day.loadInput();
        day.prepareData();

        // when
        long result = day.calculateFirstStar(2000000);

        // then
        assertEquals(4748135, result);
    }

    @Test
    public void calculateSecondStarTestData() throws IOException {
        // given
        Day15 day = new Day15();
        day.setFilename("day-15-test");
        day.loadInput();
        day.prepareData();

        // when
        long result = day.calculateSecondStar(20);

        // then
        assertEquals(56000011, result);
    }

    @Test
    public void calculateSecondStarRealData() throws IOException {
        // given
        Day15 day = new Day15();
        day.setFilename("day-15");
        day.loadInput();
        day.prepareData();

        // when
        long result = day.calculateSecondStar(4000000);

        // then
        assertEquals(56000011, result);
    }
}
