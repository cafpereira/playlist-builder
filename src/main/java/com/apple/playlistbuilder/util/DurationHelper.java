package com.apple.playlistbuilder.util;

/**
 * Utility class to help with duration conversions
 */
public final class DurationHelper {

    public static final String convertDurationFormat(final long durationInSeconds) {
        int minutes = (int) (durationInSeconds / 60);
        long seconds = durationInSeconds - minutes * 60;
        return  String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
    }

    /**
     * Utility class should not be constructed
     */
    private DurationHelper() {
    }
}
