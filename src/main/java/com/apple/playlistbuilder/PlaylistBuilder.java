package com.apple.playlistbuilder;

import java.io.*;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 * Class responsible for reading a list of albums from a given input file and
 * and returning a playlist made of songs that have duration shorter than the
 * median of all songs.
 *
 * This implementation use optimized tree data structures to calculate the
 * median value of the list without sorting the entire list.
 */
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

    /**
     * Load all songs from the album list, calculate the median duration
     * and generate the playlist.
     *
     * @return
     * @throws Exception
     */
    public Playlist generatePlaylist() throws Exception {

        // Optimized implementation of song loading process that keeps two binary trees:
        // a) maxHeap with all elements from the lower half of the song list and
        // b) minHeap with upper half songs.
        loadAlbumsFromFile(bufferedReader);

        // Using both heaps we can compute the median value in constant time O(1)
        double median = calculateMedian(maxHeap, minHeap);

        // Then we iterate over the first lower half and add all songs to the final playlist
        Playlist playlist = new Playlist();
        while (!maxHeap.isEmpty()){
            playlist.addSong(maxHeap.remove());
        }
        // Reverse to list songs in order from shortest to longest.
        Collections.reverse(playlist.getSongs());

        // Finally, we look for songs with duplicated duration values on the other tree
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
            int songId = 1;
            for (Song song : playlist.getSongs()) {
                writer.println(songId + ". " + song.parseToOutputFormat());
                songId++;
            }
            writer.println(playlist.parseDurationToOutputFormat());
            writer.close();
        } catch (IOException eio) {
            eio.printStackTrace();
        }
    }


    /**
     * Calculates the median of all songs.
     *
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
     * Load all songs from the input list into two heap structures, one with songs that have duration less
     * than the median and another song duration higher than the median.
     *
     * @param inputReader
     * @throws Exception
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
                // Start consuming list of songs of the new album
                Song nextSong = Song.parseFrom(line);
                nextSong.setAlbum(newAlbum);

                // Add next song either to the lower half or upper half
                if (minHeap.isEmpty()) {
                    minHeap.add(nextSong);
                } else {
                    if (nextSong.compareTo(minHeap.peek()) >= 0) {
                        minHeap.add(nextSong);
                    } else {
                        maxHeap.add(nextSong);
                    }
                }
                // If tree sizes are unbalanced, move the smallest (or largest) element
                // to the tree that has less elements
                if (minHeap.size() > maxHeap.size() + 1) {
                    maxHeap.add(minHeap.remove());
                } else if (maxHeap.size() > minHeap.size()) {
                    minHeap.add(maxHeap.remove());
                }
            }
        }
    }
}
