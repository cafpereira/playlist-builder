package com.apple.playlistbuilder;

import com.apple.playlistbuilder.exceptions.AlbumFormatException;

public class Album {
    private String name;
    private int year;
    private String artistName;

    /**
     * Converts line "[artist] / [album-name] / [album-year]" to an Album
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

    @Override
    public int hashCode() {
        int h = 5381;
        h += (h << 5) + name.hashCode();
        h += (h << 5) + Integer.hashCode(year);
        h += (h << 5) + artistName.hashCode();
        return h;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
}
