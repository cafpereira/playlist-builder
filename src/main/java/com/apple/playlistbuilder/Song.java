package com.apple.playlistbuilder;

import com.apple.playlistbuilder.exceptions.SongFormatException;

import java.text.MessageFormat;
import java.util.Objects;

public class Song implements Comparable<Song>{
    private final MessageFormat outputFormat = new MessageFormat("{0} - {0} - {0} - {0} - {0}");

    private Album album;
    private String name;
    private long durationInSeconds;

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(final Album album) {
        this.album = album;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public long getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(final long durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public void setDuration(final long minutes, final long seconds) {
        this.durationInSeconds = minutes * 60 + seconds;
    }


    /**
     * Converts line "Making Plans for Nigel - 4:14" to an Song
     */
    public static Song parseFrom(final String line) throws SongFormatException {
        String[] info = line.split("-");
        if (info.length != 2) {
            throw new SongFormatException("Wrong song information");
        }

        String[] time = info[1].trim().split(":");
        if (time.length != 2) {
            throw new SongFormatException("Wrong song duration");
        }

        Song song = new Song();
        song.setName(info[0].trim());
        song.setDuration(Long.valueOf(time[0]), Long.valueOf(time[1]));

        return song;
    }

    /**
     * Convert to output format: Artist - Album - Year - Song - Song Length
     */
    public String parseToOutputFormat() {
        return outputFormat.format(new Object[]{ album.getArtistName(), album.getName(), album.getYear(), name,
                DurationHelper.convertDurationFormat(this.durationInSeconds)});
    }

    @Override
    public int compareTo(Song o) {
        return (int) (this.durationInSeconds - o.durationInSeconds);
    }

    /**
     * This instance is equal to all instances of {@code ImmutablePlaylistInterface} that have equal attribute values.
     * @return {@code true} if {@code this} is equal to {@code another} instance
     */
    @Override
    public boolean equals(Object another) {
        if (this == another) return true;
        return another instanceof Song
                && equalTo((Song) another);
    }

    private boolean equalTo(Song another) {
        return album.equals(another.getAlbum())
                && name.equals(another.getName())
                && durationInSeconds == another.getDurationInSeconds();
    }

    /**
     * Computes a hash code from attributes: {@code songs}, {@code totalDurationSeconds}.
     * @return hashCode value
     */
    @Override
    public int hashCode() {
        int h = 5381;
        h += (h << 5) + album.hashCode();
        h += (h << 5) + name.hashCode();
        h += (h << 5) + Long.hashCode(durationInSeconds);
        return h;
    }
}
