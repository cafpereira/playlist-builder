package com.apple.playlistbuilder;

/**
 * Utility class to help with duration conversions
 */
public final class DurationHelper {

    public static final String convertDurationFormat(final long durationInSeconds){
        int minutes = (int) (durationInSeconds%60);
        long seconds = durationInSeconds - minutes*60;
        return  String.format("%02d", String.valueOf(minutes)) + ":" + String.format("%02d", String.valueOf(seconds));
    }

    /**
     * Utility class should not be constructed
     */
    private DurationHelper() {
    }
}
