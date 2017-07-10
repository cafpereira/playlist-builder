package com.apple.playlistbuilder;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by cpereira on 09/07/17.
 */
public class DurationHelperTest {

    @Test
    public void covertZeroSeconds(){
        long seconds = 0;
        String expected = "0:0";
        String actual = DurationHelper.convertDurationFormat(seconds);

        Assert.assertEquals("Convert Zero seconds: ", actual, expected);
    }
}
