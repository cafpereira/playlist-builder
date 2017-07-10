package com.apple.playlistbuilder;

import com.apple.playlistbuilder.exceptions.AlbumFormatException;

public class Album {

    private String name;
    private int year;
    private String artistName;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(final int year) {
        this.year = year;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(final String artistName) {
        this.artistName = artistName;
    }

    /**
     * Converts line "XTC / Drums and Wires / 1979" to an Album
     */
    public static Album parseFrom(final String line) throws AlbumFormatException {

        String[] info = line.split("/");
        if (info.length != 3) {
            throw new AlbumFormatException("Wrong album information: " + line);
        }

        Album album = new Album();
        album.setArtistName(info[0].trim());
        album.setName(info[1].trim());
        album.setYear(Integer.valueOf(info[2].trim()));
        return album;
    }

    /**
     * This instance is equal to all instances of {@code ImmutablePlaylistInterface} that have equal attribute values.
     * @return {@code true} if {@code this} is equal to {@code another} instance
     */
    @Override
    public boolean equals(Object another) {
        if (this == another) return true;
        return another instanceof Album
                && equalTo((Album) another);
    }

    private boolean equalTo(Album another) {
        return name.equals(another.getName())
                && year == another.getYear()
                && artistName.equals(another.getArtistName());
    }

    /**
     * Computes a hash code from attributes: {@code songs}, {@code totalDurationSeconds}.
     * @return hashCode value
     */
    @Override
    public int hashCode() {
        int h = 5381;
        h += (h << 5) + name.hashCode();
        h += (h << 5) + Integer.hashCode(year);
        h += (h << 5) + artistName.hashCode();
        return h;
    }
}
