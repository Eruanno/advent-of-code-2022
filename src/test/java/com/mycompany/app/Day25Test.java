package com.mycompany.app;

import org.junit.Test;

import java.io.IOException;

import static java.math.BigInteger.*;
import static org.junit.Assert.assertEquals;

public class Day25Test {

    @Test
    public void calculateFirstStarTestData() throws IOException {
        // given
        Day sut = new Day25("day-25-test");

        // when
        String result = sut.calculateFirstStar();

        // then
        assertEquals("2=-1=0", result);
    }

    @Test
    public void calculateFirstStarRealData() throws IOException {
        // given
        Day sut = new Day25("day-25");

        // when
        String result = sut.calculateFirstStar();

        // then
        assertEquals("20=212=1-12=200=00-1", result);
    }

    @Test
    public void snafuToDecimal() throws IOException {
        // given
        Day25 sut = new Day25("day-25-test");

        // then
        assertEquals(valueOf(-2), sut.snafuToDecimal("=")); // -2
        assertEquals(valueOf(-1), sut.snafuToDecimal("-")); // -2
        assertEquals(ZERO, sut.snafuToDecimal("0")); // 0
        assertEquals(ONE, sut.snafuToDecimal("1")); // 1
        assertEquals(TWO, sut.snafuToDecimal("2")); // 2
        assertEquals(valueOf(3), sut.snafuToDecimal("1=")); // 5 - 2
        assertEquals(valueOf(4), sut.snafuToDecimal("1-")); // 5 - 1
        assertEquals(valueOf(5), sut.snafuToDecimal("10")); // 5 - 0
        assertEquals(valueOf(6), sut.snafuToDecimal("11")); // 5 + 1
        assertEquals(valueOf(7), sut.snafuToDecimal("12")); // 5 + 2
        assertEquals(valueOf(8), sut.snafuToDecimal("2=")); // 2 * 5 - 2
        assertEquals(valueOf(9), sut.snafuToDecimal("2-")); // 2 * 5 - 1
        assertEquals(TEN, sut.snafuToDecimal("20")); // 2 * 5 - 0
    }

    @Test
    public void decimalToSnafu() throws IOException {
        // given
        Day25 sut = new Day25("day-25-test");

        // then
        assertEquals("1", sut.decimalToSnafu(ONE)); // 0 * 5 + 1
        assertEquals("2", sut.decimalToSnafu(TWO)); // 0 * 5 + 2
        assertEquals("1=", sut.decimalToSnafu(valueOf(3))); // 1 * 5 - 2
        assertEquals("1-", sut.decimalToSnafu(valueOf(4))); // 1 * 5 - 1
        assertEquals("10", sut.decimalToSnafu(valueOf(5))); // 1 * 5 - 0
        assertEquals("11", sut.decimalToSnafu(valueOf(6))); // 1 * 5 + 1
        assertEquals("12", sut.decimalToSnafu(valueOf(7))); // 1 * 5 + 2
        assertEquals("2=", sut.decimalToSnafu(valueOf(8))); // 2 * 5 - 2
        assertEquals("2-", sut.decimalToSnafu(valueOf(9))); // 2 * 5 - 1
        assertEquals("20", sut.decimalToSnafu(TEN)); // 2 * 5 + 0
        assertEquals("1=0", sut.decimalToSnafu(valueOf(15))); // 3 * 5 - 2 * -2 + 0
        assertEquals("1-0", sut.decimalToSnafu(valueOf(20)));
        assertEquals("1=11-2", sut.decimalToSnafu(valueOf(2022)));
        assertEquals("1-0---0", sut.decimalToSnafu(valueOf(12345)));
        assertEquals("1121-1110-1=0", sut.decimalToSnafu(valueOf(314159265)));
    }
}
