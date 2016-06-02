package com.example.abhishek.smush;


/**
 * Edited by protino
 */

import java.util.*;


public class Song {

    public long id;
    public String name;
    public long time_duration;
    public String artist_name;
    public String full_path;
    public int[] trade_secret;

    public Song(long id,
                String name,
                long time_duration,
                String artist_name,
                String full_path,
                int[] trade_secret) {

        this.id = id;
        this.name = name;
        this.time_duration = time_duration;
        this.artist_name = artist_name;
        this.full_path = full_path;

    }

}
