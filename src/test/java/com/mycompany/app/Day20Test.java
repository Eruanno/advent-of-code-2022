package com.mycompany.app;

import org.junit.Test;

import static java.lang.Math.abs;
import static org.junit.Assert.assertEquals;


public class Day20Test {

    @Test
    public void test() {
        for (int i = -14; i < 21; i++) {
            int m;
            if (i < 0) {
                m = abs((i - 7 + 1) / 7);
            } else {
                m = i / 7;
            }
            System.out.printf("i: %d\tl: %d\tm: %d%n", i, 7, m);
        }
    }

    @Test
    public void clampDestination() {
        Day20 day = new Day20("day-20");
        int length = 7;
        // In second negative range.
        assertEquals(4, day.clampDestination(-14, length));
        assertEquals(5, day.clampDestination(-13, length));
        assertEquals(0, day.clampDestination(-12, length));
        assertEquals(1, day.clampDestination(-11, length));
        assertEquals(2, day.clampDestination(-10, length));
        assertEquals(3, day.clampDestination(-9, length));
        assertEquals(4, day.clampDestination(-8, length));
        // In first negative range.
        assertEquals(5, day.clampDestination(-7, length));
        assertEquals(0, day.clampDestination(-6, length));
        assertEquals(1, day.clampDestination(-5, length));
        assertEquals(2, day.clampDestination(-4, length));
        assertEquals(3, day.clampDestination(-3, length));
        assertEquals(4, day.clampDestination(-2, length));
        assertEquals(5, day.clampDestination(-1, length));
        // In normal range.
        assertEquals(0, day.clampDestination(0, length));
        assertEquals(1, day.clampDestination(1, length));
        assertEquals(2, day.clampDestination(2, length));
        assertEquals(3, day.clampDestination(3, length));
        assertEquals(4, day.clampDestination(4, length));
        assertEquals(5, day.clampDestination(5, length));
        assertEquals(6, day.clampDestination(6, length));
        // In first positive range.
        assertEquals(1, day.clampDestination(7, length));
        assertEquals(2, day.clampDestination(8, length));
        assertEquals(3, day.clampDestination(9, length));
        assertEquals(4, day.clampDestination(10, length));
        assertEquals(5, day.clampDestination(11, length));
        assertEquals(6, day.clampDestination(12, length));
        assertEquals(1, day.clampDestination(13, length));
        // In second positive range.
        assertEquals(2, day.clampDestination(14, length));
        assertEquals(3, day.clampDestination(15, length));
        assertEquals(4, day.clampDestination(16, length));
        assertEquals(5, day.clampDestination(17, length));
        assertEquals(6, day.clampDestination(18, length));
        assertEquals(1, day.clampDestination(19, length));
        assertEquals(2, day.clampDestination(20, length));
    }

/*    @Test
    public void mixArray() {
        int[] array = new int[5];
        array[0] = 1;
        array[1] = 2;
        array[2] = 3;
        array[3] = 4;
        array[4] = 5;
        Day20 day = new Day20();

        day.mixArray(array, 0, 4);
        assertEquals(2, array[0]);
        assertEquals(3, array[1]);
        assertEquals(4, array[2]);
        assertEquals(5, array[3]);
        assertEquals(1, array[4]);

        day.mixArray(array, 4, 0);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);
        assertEquals(4, array[3]);
        assertEquals(5, array[4]);

        day.mixArray(array, 2, 4);
        assertEquals(2, array[0]);
        assertEquals(3, array[1]);
        assertEquals(5, array[2]);
        assertEquals(1, array[3]);
        assertEquals(4, array[4]);

        day.mixArray(array, 2, 0);
        assertEquals(5, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);
        assertEquals(1, array[3]);
        assertEquals(4, array[4]);


        day.mixArray(array, 4, 2);
        assertEquals(5, array[0]);
        assertEquals(2, array[1]);
        assertEquals(4, array[2]);
        assertEquals(3, array[3]);
        assertEquals(1, array[4]);

        day.mixArray(array, 0, 2);
        assertEquals(2, array[0]);
        assertEquals(4, array[1]);
        assertEquals(5, array[2]);
        assertEquals(3, array[3]);
        assertEquals(1, array[4]);
    }*/
}