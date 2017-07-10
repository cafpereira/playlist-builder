package com.apple.playlistbuilder.exceptions;

/**
 * Exception throw when the song line does not respect the expected format.
 */
public class OutOfBoundDurationException extends Exception {
    public OutOfBoundDurationException(String message) {
        super("Duration over maximum limit: " + message);
    }
}
