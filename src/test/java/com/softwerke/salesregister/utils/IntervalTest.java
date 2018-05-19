package com.softwerke.salesregister.utils;

import org.junit.Test;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.function.Function;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IntervalTest {
    @Test(expected = NullPointerException.class)
    public void Interval_Null_NPE() {
        new Interval<String>(null);
    }

    @Test(expected = NullPointerException.class)
    public void Interval_TwoNulls_NPE() {
        new Interval<>(null, (String) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void Interval_NullStringAndNullFunction_IllegalArgumentException() {
        new Interval<>(null, (Function<String, String>) null);
    }

    @Test
    public void contains_SingleBound_Success() {
        Interval<String> stringInterval = new Interval<>("2");

        assertTrue(stringInterval.contains("2"));
        assertFalse(stringInterval.contains("0"));
    }

    @Test
    public void contains_CoupleBounds_Success() {
        Interval<Integer> stringInterval = new Interval<>(2, 4);

        assertTrue(stringInterval.contains(2));
        assertTrue(stringInterval.contains(3));
        assertTrue(stringInterval.contains(4));
        assertFalse(stringInterval.contains(0));
    }

    @Test
    public void contains_CoupleBoundsStringAndParserFunction_Success() {
        Interval<ChronoLocalDate> stringInterval = new Interval<ChronoLocalDate>("01 10 2010 31 12 2010",
                Utils::parseStringToLocalDate);

        assertTrue(stringInterval.contains(LocalDate.of(2010, 10, 1)));
        assertTrue(stringInterval.contains(LocalDate.of(2010, 11, 15)));
        assertTrue(stringInterval.contains(LocalDate.of(2010, 12, 31)));
        assertFalse(stringInterval.contains(LocalDate.now()));
    }

    @Test
    public void contains_SingleBoundStringAndParserFunction_Success() {
        Interval<Double> stringInterval = new Interval<Double>("0.99", Double::new);

        assertTrue(stringInterval.contains(0.99));
        assertFalse(stringInterval.contains(1.0));
        assertFalse(stringInterval.contains(0.98));
    }
}
