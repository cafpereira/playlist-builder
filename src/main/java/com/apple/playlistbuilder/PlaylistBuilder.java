package com.apple.playlistbuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

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
    private static PriorityQueue<Song> minHeap = new PriorityQueue<Song>();
    private static PriorityQueue<Song> maxHeap = new PriorityQueue<Song>();
    private static List<String> playlist;
    BufferedReader bufferedReader;


    public static void main(String[] args) throws Exception {
        String fileName = "Albums.txt";
        String outputFilename = "Playlist.txt";

        BufferedReader bufferedReader = getReader(fileName);
        generatePlaylist(bufferedReader);
        writePlaylistFile(outputFilename, playlist);
    }

    public static void generatePlaylist(final BufferedReader bufferedReader) throws IOException {

        fetchFileToHeap(bufferedReader);
        double median = calculateMedian();
        long totalSeconds = 0;

        Song song;
        playlist = new ArrayList<>(maxHeap.size());
        for (int i = maxHeap.size()-1; i>=0; i--) {
            song = maxHeap.remove();
            playlist.set(i, song.parseToOutputFormat());
            totalSeconds += song.getDurationInSeconds();
        }

        while (minHeap.peek().getDurationInSeconds() <= median) {
            song = minHeap.remove();
            playlist.add(song.parseToOutputFormat());
            totalSeconds += song.getDurationInSeconds();
        }

        playlist.add("Total Time: " + DurationHelper.convertDurationFormat(totalSeconds));
    }


    /**
     * Transfer from heap to playlist file
     */
    public static void writePlaylistFile(final String outputFilename, final List<String> playlist){
        try {
            PrintWriter writer = new PrintWriter(outputFilename, "UTF-8");
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
    public static double calculateMedian(){
        return minHeap.size() == maxHeap.size()
                ? 0.5 * (minHeap.peek().getDurationInSeconds() + maxHeap.peek().getDurationInSeconds())
                : minHeap.peek().getDurationInSeconds();
    }

    public static BufferedReader getReader(final String filename) throws FileNotFoundException {
        FileReader fileReader = new FileReader(filename);
        return new BufferedReader(fileReader);
    }


    public static void fetchFileToHeap(final BufferedReader bufferedReader) throws IOException {

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

        String[] info = line.split("//");
        if (info.length != 3) {
            throw new RuntimeException("Wrong album information");
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
    private static Song generateSong(final Album album, final String line) {

        String[] info = line.split("-");
        if (info.length != 2) {
            throw new RuntimeException("Wrong song information");
        }

        String[] time = info[1].split(":");
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
