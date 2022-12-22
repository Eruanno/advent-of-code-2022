package com.mycompany.app;


import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class Day15Test {

    @Test
    public void calculateFirstStarTestData() throws IOException {
        // given
        Day15 day = new Day15("day-15-test", 10, 20);

        // when
        String result = day.calculateFirstStar();

        // then
        assertEquals("26", result);
    }

    @Test
    public void calculateFirstStarRealData() throws IOException {
        // given
        Day15 day = new Day15("day-15", 2000000, 4000000);

        // when
        String result = day.calculateFirstStar();

        // then
        assertEquals("4748135", result);
    }

    @Test
    public void calculateSecondStarTestData() throws IOException {
        // given
        Day15 day = new Day15("day-15-test", 10, 20);

        // when
        String result = day.calculateSecondStar();

        // then
        assertEquals("56000011", result);
    }

    @Test
    public void calculateSecondStarRealData() throws IOException {
        // given
        Day15 day = new Day15("day-15", 2000000, 4000000);

        // when
        String result = day.calculateSecondStar();

        // then
        assertEquals("13743542639657", result);
    }
}
