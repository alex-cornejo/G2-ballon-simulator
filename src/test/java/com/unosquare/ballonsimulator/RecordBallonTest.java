package com.unosquare.ballonsimulator;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.unosquare.ballonsimulator.model.RecordBallon;
import com.unosquare.ballonsimulator.model.Observatory.ObservatoryEnum;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.containsString;

/**
 * RecordBallonTest
 */
public class RecordBallonTest {

    @Rule
    private final ExpectedException thrown = ExpectedException.none();

    @Test
    public void createRecordBallonTest() {
        String pattern = "2014-12-31T13:44|10,5|243|AU";

        RecordBallon recordBallon = new RecordBallon(pattern);

        assertEquals(LocalDateTime.of(2014, Month.DECEMBER, 31, 13, 44), recordBallon.getTimestamp());

        assertArrayEquals(new int[] { 10, 5 }, recordBallon.getLocation());

        assertEquals(243, recordBallon.getTemperature());

        assertEquals(ObservatoryEnum.AU, recordBallon.getObservatory());

        assertEquals(recordBallon.toString(), pattern);
    }

    @Test
    public void createRecordBallonTestBadPattern() {

        // bad pattern
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(containsString("The pattern has wrong data"));
        String pattern = "2014-12-31T13:44105|243AU";
        new RecordBallon(pattern);
    }

    @Test
    public void createRecordBallonTestBadTimestamp() {

        // bad timestamp
        thrown.expect(DateTimeParseException.class);
        thrown.expectMessage(containsString("could not be parsed"));
        String pattern = "20142-31T13:44|10,5|243|AU";
        new RecordBallon(pattern);
    }

    @Test
    public void createRecordBallonTestBadLocation() {

        // bad location
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(containsString("Location has wrong data"));
        String pattern = "2014-12-31T13:44|105|243|AU";
        new RecordBallon(pattern);
    }
}