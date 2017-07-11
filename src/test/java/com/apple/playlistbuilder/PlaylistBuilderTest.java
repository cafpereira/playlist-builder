package com.apple.playlistbuilder;

import com.apple.playlistbuilder.fixtures.PlaylistFixture;
import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.BufferedReader;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class PlaylistBuilderTest {

    PlaylistBuilder playlistBuilder;
    BufferedReader mockReader;

    @BeforeMethod
    private void setUp(){
        mockReader = Mockito.mock(BufferedReader.class);
        playlistBuilder = new PlaylistBuilder(mockReader);
    }

    @Test
    public void emptyFile() throws Exception {
        when(mockReader.readLine()).thenReturn(null);
        Playlist emptyPlaylist =  new Playlist();
        assertEquals(emptyPlaylist, playlistBuilder.generatePlaylist(), "Playlist expected to be empty");
    }

    @Test
    public void singleMusicOneAlbum() throws Exception {

        when(mockReader.readLine()).thenReturn(
                "XTC / Drums and Wires / 1979",
                "Making Plans for Nigel - 4:14",
                null);

        Playlist expectedPlaylist = PlaylistFixture.createFrom("XTC / Drums and Wires / 1979",
                "Making Plans for Nigel - 4:14");

        assertEquals(expectedPlaylist, playlistBuilder.generatePlaylist());
    }

    @Test
    public void multipleMusicsSingleAlbum() throws Exception {

        when(mockReader.readLine()).thenReturn(
                "XTC / Drums and Wires / 1979",
                "Making Plans for Nigel - 4:14",
                "Helicopter - 3:55",
                "Day In Day Out - 3:08",
                "When You're Near Me I Have Difficulty - 3:22",
                null);

        Playlist expectedPlaylist = PlaylistFixture.createFrom("XTC / Drums and Wires / 1979",
                "Day In Day Out - 3:08",
                "When You're Near Me I Have Difficulty - 3:22");

        assertEquals(expectedPlaylist, playlistBuilder.generatePlaylist());
    }

    @Test
    public void multipleAlbums() throws Exception {

        when(mockReader.readLine()).thenReturn(
                "XTC / Drums and Wires / 1979",
                "Making Plans for Nigel - 4:14",
                "Helicopter - 3:55",
                "",
                "Vampire Weekend / Contra / 2010",
                "Horchata - 3:27",
                "White Sky - 2:59",
                "",
                "Love / Forever Changes / 1967",
                "Alone Again Or - 3:17",
                "A House Is Not A Motel - 3:32",
                null);

        Playlist expectedPlaylist = PlaylistFixture.createFrom("Vampire Weekend / Contra / 2010", "White Sky - 2:59");
        PlaylistFixture.addSong(expectedPlaylist, "Love / Forever Changes / 1967", "Alone Again Or - 3:17");
        PlaylistFixture.addSong(expectedPlaylist, "Vampire Weekend / Contra / 2010", "Horchata - 3:27");

        assertEquals(expectedPlaylist, playlistBuilder.generatePlaylist());
    }

    @Test
    public void multipleAlbumsRepeatedDuration() throws Exception {

        when(mockReader.readLine()).thenReturn(
                "XTC / Drums and Wires / 1979",
                "Making Plans for Nigel - 4:14",
                "Helicopter - 3:55",
                "",
                "Vampire Weekend / Contra / 2010",
                "Horchata - 3:27",
                "White Sky - 2:59",
                "",
                "Love / Forever Changes / 1967",
                "Alone Again Or - 3:17",
                "A House Is Not A Motel - 3:27",
                null);

        Playlist expectedPlaylist = PlaylistFixture.createFrom("Vampire Weekend / Contra / 2010", "White Sky - 2:59");
        PlaylistFixture.addSong(expectedPlaylist, "Love / Forever Changes / 1967", "Alone Again Or - 3:17");
        PlaylistFixture.addSong(expectedPlaylist, "Vampire Weekend / Contra / 2010", "Horchata - 3:27");
        PlaylistFixture.addSong(expectedPlaylist, "Love / Forever Changes / 1967", "A House Is Not A Motel - 3:27");

        assertEquals(expectedPlaylist, playlistBuilder.generatePlaylist());
    }
}
