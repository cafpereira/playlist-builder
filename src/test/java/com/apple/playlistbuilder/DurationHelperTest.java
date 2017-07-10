package com.apple.playlistbuilder;

import com.apple.playlistbuilder.exceptions.OutOfBoundDurationException;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Testing duration utility class.
 */
public class DurationHelperTest {

    @Test
    public void covertZeroSeconds() throws Exception {
        long seconds = 0;
        String expected = "00:00";
        String actual = DurationHelper.convertDurationFormat(seconds);

        Assert.assertEquals(actual, expected, "ERROR Convert Zero Seconds: ");
    }

    @Test
    public void covertLessThanMinute() throws Exception {
        long seconds = 30;
        String expected = "00:30";
        String actual = DurationHelper.convertDurationFormat(seconds);

        Assert.assertEquals(actual, expected, "ERROR Convert " + seconds + " Seconds: ");
    }

    @Test
    public void covertLowerLimitMinute() throws Exception{
        long seconds = 59;
        String expected = "00:59";
        String actual = DurationHelper.convertDurationFormat(seconds);

        Assert.assertEquals(actual, expected, "ERROR Convert " + seconds + " Seconds: ");
    }

    @Test
    public void covertOneMinute() throws Exception{
        long seconds = 60;
        String expected = "01:00";
        String actual = DurationHelper.convertDurationFormat(seconds);

        Assert.assertEquals(actual, expected, "ERROR Convert " + seconds + " Seconds: ");
    }

    @Test
    public void covertSeveralMinutes() throws Exception{
        long seconds = 1259;
        String expected = "20:59";
        String actual = DurationHelper.convertDurationFormat(seconds);

        Assert.assertEquals(actual, expected, "ERROR Convert " + seconds + " Seconds: ");
    }

    @Test(expectedExceptions = OutOfBoundDurationException.class)
    public void covertOutOfBoundMinutes() throws Exception{
        DurationHelper.convertDurationFormat(6001);
    }
}
