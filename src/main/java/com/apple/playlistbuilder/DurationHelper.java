package com.apple.playlistbuilder;

import com.apple.playlistbuilder.exceptions.OutOfBoundDurationException;

/**
 * Utility class to help with duration conversions
 */
public final class DurationHelper {

    private static final int MAXIMUM_SECONDS = 6000;

    public static final String convertDurationFormat(final long durationInSeconds) throws OutOfBoundDurationException {
        if (durationInSeconds >= MAXIMUM_SECONDS) {
            throw new OutOfBoundDurationException ("Requested: " + durationInSeconds +
                    "s and the maximum allowed is " + MAXIMUM_SECONDS + "s...");
        }

        int minutes = (int) (durationInSeconds/60);
        long seconds = durationInSeconds - minutes*60;
        return  String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
    }

    /**
     * Utility class should not be constructed
     */
    private DurationHelper() {
    }
}
