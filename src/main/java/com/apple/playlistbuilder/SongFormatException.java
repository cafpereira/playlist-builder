package com.apple.playlistbuilder;

/**
 * Exception throw when the song line does not respect the expected format.
 */
public class SongFormatException extends Exception {
    public SongFormatException(String message) {
        super("Invalid Song Format: " + message);
    }
}
