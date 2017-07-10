package com.apple.playlistbuilder;

/**
 * Created by cpereira on 09/07/17.
 */
public class Song  implements Comparable<Song>{
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
        this.durationInSeconds = minutes*60 + seconds;
    }

    /**
     * Convert to output format: Artist - Album - Year - Song - Song Length
     */
    public String parseToOutputFormat(){

        return  this.album.getArtistName() + " - " + this.album.getName() + " - " + this.album.getYear() + " - " +
                name + " - " + DurationHelper.convertDurationFormat(this.durationInSeconds);
    }

    public int compareTo(Song o) {
        return (int) (this.durationInSeconds - o.durationInSeconds);
    }
}
