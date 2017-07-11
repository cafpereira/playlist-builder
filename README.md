# Playlist Builder

## Description
This application permits you to make a playlist featuring short songs from albums 
from different decades. Once you input a list of albums and their corresponding songs, 
this application will generate a list of songs for your playlist. 

The playlist will be created based in the median song in terms of length, 
it will contain a list of songs and their total time.

The criteria used to generate your playlist it that all songs duration presented 
in it should be less than or equal to the median song in terms of length.  
FInally, these songs in your playlist will be presented in order from shortest to longest

## Albums Input
The application expected a source data file in the following format:

<pre><code>
[Artist] / [Album] / [Year]
[Song 1] - [Song 1 length in m:ss]
[Song 2] - [Song 2 length in m:ss]
...
[Song N] - [Song N length in m:ss]
<br>
[Artist] / [Album] / [Year]
[Song 1] - [Song 1 length in m:ss]
[Song 2] - [Song 2 length in m:ss]
...
[Song N] - [Song N length in m:ss]
<br>
[Artist] / [Album] / [Year]
[Song 1] - [Song 1 length in m:ss]
[Song 2] - [Song 2 length in m:ss]
...
[Song N] - [Song N length in m:ss]
</code></pre>

## Playlist Output

Once the playlist is generated, you should see the following structure:

<pre><code>
1. Artist - Album - Year - Song - Song Length
2. Artist - Album - Year - Song - Song Length
...
[N]. Artist - Album - Year - Song - Song Length
Total Time: [mm:ss]
</code></pre>

## How to Use

Make sure to have your Album file following the input criteria

In order to generate the playlist you should execute the application 
*playlist-builder* from your terminal. See command bellow:
> **java -jar playlist-builder.jar -input [inputFilePath] -output [outputFilePath]**

For more usage help, run with the flag *--help*
