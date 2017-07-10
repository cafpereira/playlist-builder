package com.apple.playlistbuilder;

import java.io.*;
import java.util.*;

// I have decided to make a playlist featuring short songs from three albums from three different decades.
// Given a list of albums and their corresponding songs, output a list of songs in order from shortest to longest,
// where each song is less than or equal to the median song in terms of length.  Finally, output the total time of
// the playlist.
//
// The source data file (Albums.txt) is in the following format:
//
// <Artist> / <Album> / <Year>
// <Song 1> - <Song 1 length in m:ss>
// <Song 2> - <Song 2 length in m:ss>
//        ...
// <Song N> - <Song N length in m:ss>
//
// <Artist> / <Album> / <Year>
// <Song 1> - <Song 1 length in m:ss>
// <Song 2> - <Song 2 length in m:ss>
//        ...
// <Song N> - <Song N length in m:ss>
//
// <Artist> / <Album> / <Year>
// <Song 1> - <Song 1 length in m:ss>
// <Song 2> - <Song 2 length in m:ss>
//        ...
// <Song N> - <Song N length in m:ss>
//
//
// Your output should be like this:
//
// 1. Artist - Album - Year - Song - Song Length
// 2. Artist - Album - Year - Song - Song Length
//    ...
// <N>. Artist - Album - Year - Song - Song Length
// Total Time: <mm:ss>
//
//
// Use any language you like and make it runnable from the command line.
// If your runnable is compiled, make sure to provide uncompiled source.
//
//
public class PlaylistBuilder {

    private BufferedReader bufferedReader;

    private  final int DEFAULT_INITIAL_CAPACITY = 10;
    private PriorityQueue<Song> minHeap = new PriorityQueue<>();
    private PriorityQueue<Song> maxHeap =
            new PriorityQueue<Song>(DEFAULT_INITIAL_CAPACITY, Collections.reverseOrder());


    public PlaylistBuilder(final String inputFilePath) throws FileNotFoundException {
        this(new BufferedReader(new FileReader(inputFilePath)));
    }

    public PlaylistBuilder(final BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }


    public static void main(String[] args) throws Exception {
        String fileName = "Albums.txt";
        PlaylistBuilder builder = new PlaylistBuilder(fileName);
        Playlist playlist = builder.generatePlaylist();

        String outputFilename = "Playlist.txt";
        builder.writePlaylistFile(playlist, outputFilename);
    }

    public Playlist generatePlaylist() throws Exception {

        loadAlbumsFromFile(bufferedReader);
        double median = calculateMedian(maxHeap, minHeap);

        Playlist playlist = new Playlist();

        while (!maxHeap.isEmpty()){
            playlist.addSong(maxHeap.remove());
        }
        // Reverse to list songs in order from shortest to longest.
        Collections.reverse(playlist.getSongs());

        while (!minHeap.isEmpty() && minHeap.peek().getDurationInSeconds() <= median) {
            playlist.addSong(minHeap.remove());
        }
        return playlist;
    }


    /**
     * Transfer from heap to playlist file
     */
    public void writePlaylistFile(Playlist playlist, final String outputFilePath) {
        try {
            PrintWriter writer = new PrintWriter(outputFilePath, "UTF-8");
            for (Song song : playlist.getSongs()) {
                writer.println(song.parseToOutputFormat());
            }
            writer.println(playlist.parseDurationToOutputFormat());
            writer.close();
        } catch (IOException eio) {
            eio.printStackTrace();
        }
    }


    /**
     * Returns Median duration in seconds
     * @param lowerHalf
     * @param upperHalf
     */
    private double calculateMedian(PriorityQueue<Song> lowerHalf, PriorityQueue<Song> upperHalf){
        if ((lowerHalf.size() == 0) && (upperHalf.size() == 0)) {
            return -1;
        } else if (upperHalf.size() == 0) {
            return lowerHalf.peek().getDurationInSeconds();
        } else if (lowerHalf.size() == 0) {
            return upperHalf.peek().getDurationInSeconds();
        } else {
            return upperHalf.size() == lowerHalf.size()
                    ? 0.5 * (upperHalf.peek().getDurationInSeconds() + lowerHalf.peek().getDurationInSeconds())
                    : upperHalf.peek().getDurationInSeconds();
        }
    }

    /**
     * Read the text file to add bad words to an array.
     * Save each music of the file in the heap
     * @param inputReader
     * @throws IOException
     */
    private void loadAlbumsFromFile(final BufferedReader inputReader) throws Exception {
        String line;
        boolean startNewAlbum = true;
        Album newAlbum = null;

        while ((line = inputReader.readLine()) != null) {
            if (startNewAlbum) {
                newAlbum = Album.parseFrom(line);
                startNewAlbum = false;
            } else if (line.trim().isEmpty()) {
                startNewAlbum = true;
            } else {
                Song nextSong = Song.parseFrom(line);
                nextSong.setAlbum(newAlbum);

                // Saves using Heap
                if (minHeap.isEmpty()) {
                    minHeap.add(nextSong);
                } else {
                    if (nextSong.compareTo(minHeap.peek()) >= 0) {
                        minHeap.add(nextSong);
                    } else {
                        maxHeap.add(nextSong);
                    }
                }
                // balance minHeap and maxHeap
                if (minHeap.size() > maxHeap.size() + 1) {
                    maxHeap.add(minHeap.remove());
                } else if (maxHeap.size() > minHeap.size()) {
                    minHeap.add(maxHeap.remove());
                }
            }
        }
    }
}
