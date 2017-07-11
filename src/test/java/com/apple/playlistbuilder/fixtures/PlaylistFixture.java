package com.apple.playlistbuilder.fixtures;

import com.apple.playlistbuilder.Album;
import com.apple.playlistbuilder.Playlist;
import com.apple.playlistbuilder.Song;

/**
 * Utility class used to provide sample test data.
 */
public class PlaylistFixture {

    public static Playlist createFrom(String albumTitle, String... songs) throws Exception {
        Playlist newPlaylist = new Playlist();
        Album album = Album.parseFrom(albumTitle);
        for  (String s : songs) {
            Song song = Song.parseFrom(s);
            song.setAlbum(album);
            newPlaylist.addSong(song);
        }
        return newPlaylist;
    }

    public static void addSong(Playlist playlist, String albumTitle, String songName) throws Exception {
        Song song = Song.parseFrom(songName);
        song.setAlbum(Album.parseFrom(albumTitle));
        playlist.addSong(song);
    }
}
