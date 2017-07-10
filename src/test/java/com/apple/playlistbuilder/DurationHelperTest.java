package com.apple.playlistbuilder;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Testing duration utility class.
 */
public class DurationHelperTest {

    @Test
    public void covertZeroSeconds(){
        long seconds = 0;
        String expected = "00:00";
        String actual = DurationHelper.convertDurationFormat(seconds);

        Assert.assertEquals(actual, expected, "ERROR Convert Zero Seconds: ");
    }

    @Test
    public void covertLessThanMinute(){
        long seconds = 30;
        String expected = "00:30";
        String actual = DurationHelper.convertDurationFormat(seconds);

        Assert.assertEquals(actual, expected, "ERROR Convert 30 Seconds: ");
    }
}
