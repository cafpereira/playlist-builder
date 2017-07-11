# Coding Exercise

## Playlist Builder App
I have decided to make a playlist featuring short songs from three albums from three different decades.
Given a list of albums and their corresponding songs, output a list of songs in order from shortest to longest,
where each song is less than or equal to the median song in terms of length.  Finally, output the total time of
the playlist.

## Problem Input
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