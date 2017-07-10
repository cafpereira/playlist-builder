package com.apple.playlistbuilder;

import com.apple.playlistbuilder.exceptions.OutOfBoundDurationException;

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
    private PriorityQueue<Song> minHeap = new PriorityQueue<Song>();
    private PriorityQueue<Song> maxHeap =
            new PriorityQueue<Song>(DEFAULT_INITIAL_CAPACITY, Collections.reverseOrder());
    private List<String> playlist;


    public PlaylistBuilder(final String inputFilePath) throws FileNotFoundException {
        this(new BufferedReader(new FileReader(inputFilePath)));
    }

    public PlaylistBuilder(final BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }


    public static void main(String[] args) throws Exception {
        String fileName = "Albums.txt";
        String outputFilename = "Playlist.txt";

        PlaylistBuilder builder = new PlaylistBuilder(fileName);
        builder.generatePlaylist();
        builder.writePlaylistFile(outputFilename);
    }

    public List<String> generatePlaylist() throws IOException, OutOfBoundDurationException {

        fetchFileToHeap(bufferedReader);
        double median = calculateMedian();
        long totalSeconds = 0;

        Song song;
        playlist = new ArrayList<>();
        while (!maxHeap.isEmpty()){
            song = maxHeap.remove();
            playlist.add(song.parseToOutputFormat());
            totalSeconds += song.getDurationInSeconds();
        }
        // Reverse to list songs in order from shortest to longest.
        Collections.reverse(playlist);

        while (!minHeap.isEmpty() && minHeap.peek().getDurationInSeconds() <= median) {
            song = minHeap.remove();
            playlist.add(song.parseToOutputFormat());
            totalSeconds += song.getDurationInSeconds();
        }

        playlist.add("Total Time: " + DurationHelper.convertDurationFormat(totalSeconds));
        return playlist;
    }


    /**
     * Transfer from heap to playlist file
     */
    public void writePlaylistFile(final String outputFilePath){
        try {
            PrintWriter writer = new PrintWriter(outputFilePath, "UTF-8");
            for (int i = 0; i<playlist.size(); i++) {
                writer.print(playlist.get(i));
            }
            writer.println("The first line");
            writer.println("The second line");
            writer.close();
        } catch (IOException eio) {
            eio.printStackTrace();
        }
    }


    /**
     * Returns Median duration in seconds
     */
    private double calculateMedian(){
        if ((minHeap.size() == 0) && (maxHeap.size() == 0)) {
            return -1;
        } else if (minHeap.size() == 0) {
            return maxHeap.peek().getDurationInSeconds();
        } else if (maxHeap.size() == 0) {
            return minHeap.peek().getDurationInSeconds();
        } else {
            return minHeap.size() == maxHeap.size()
                    ? 0.5 * (minHeap.peek().getDurationInSeconds() + maxHeap.peek().getDurationInSeconds())
                    : minHeap.peek().getDurationInSeconds();
        }
    }

    private void fetchFileToHeap(final BufferedReader bufferedReader) throws IOException {

        //Read the text file to add bad words to an array
        // Save each music of the file in the heap
        String line;
        boolean isToStartNewAlbum = true;
        Album album = null;
        Song song;

        while ((line = bufferedReader.readLine()) != null) {
            if (isToStartNewAlbum) {
                album = generateAlbum(line);
                isToStartNewAlbum = false;
            } else if (line.trim().isEmpty()) {
                isToStartNewAlbum = true;
            } else {
                song = generateSong(album, line);

                // Saves using Heap
                if (minHeap.isEmpty()) {
                    minHeap.add(song);
                } else {
                    if (song.compareTo(minHeap.peek()) >= 0) {
                        minHeap.add(song);
                    } else {
                        maxHeap.add(song);
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

    /**
     * Converts line "XTC / Drums and Wires / 1979" to an Album
     */
    private static Album generateAlbum(final String line) {

        String[] info = line.split("/");
        if (info.length != 3) {
            throw new RuntimeException("Wrong album information: " + line);
        }

        Album album = new Album();
        album.setArtistName(info[0].trim());
        album.setName(info[1].trim());
        album.setYear(Integer.valueOf(info[2].trim()));
        return album;
    }

    /**
     * Converts line "Making Plans for Nigel - 4:14" to an Song
     */
    private Song generateSong(final Album album, final String line) {

        String[] info = line.split("-");
        if (info.length != 2) {
            throw new RuntimeException("Wrong song information");
        }

        String[] time = info[1].trim().split(":");
        if (info.length != 2) {
            throw new RuntimeException("Wrong song duration");
        }

        Song song = new Song();
        song.setAlbum(album);
        song.setName(info[0].trim());
        song.setDuration(Long.valueOf(time[0]), Long.valueOf(time[1]));
        return song;
    }
}
