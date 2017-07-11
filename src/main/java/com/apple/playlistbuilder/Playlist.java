package com.apple.playlistbuilder;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private final List<Song> songs = new ArrayList<>();
    private long totalDurationSeconds = 0L;

    public void addSong(Song song) {
        songs.add(song);
        this.totalDurationSeconds += song.getDurationInSeconds();
    }

    public List<Song> getSongs() {
        return songs;
    }

    public long getTotalDurationSeconds() {
        return totalDurationSeconds;
    }

    public String parseDurationToOutputFormat() {
        return  "Total Time: " + DurationHelper.convertDurationFormat(totalDurationSeconds);
    }

    @Override
    public boolean equals(Object another) {
        if (this == another) return true;
        return another instanceof Playlist
                && equalTo((Playlist) another);
    }

    private boolean equalTo(Playlist another) {
        return songs.equals(another.getSongs())
                && totalDurationSeconds == another.totalDurationSeconds;
    }

    @Override
    public int hashCode() {
        int h = 5381;
        h += (h << 5) + songs.hashCode();
        h += (h << 5) + Long.hashCode(totalDurationSeconds);
        return h;
    }
}
