package com.apple.playlistbuilder.exceptions;

/**
 * Exception throw when the album line does not respect the expected format.
 */
public class AlbumFormatException extends Exception {

    public AlbumFormatException(String message) {
        super("Invalid Album Format: " + message);
    }
}
