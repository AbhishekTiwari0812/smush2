package com.example.abhishek.smush;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Abhishek on 02-06-2016.
 */
public class SongPlayerService extends Service {
    static Map<Long, Song> SONGS_IN_PHONE;
    static Context CONTEXT;
    MediaPlayer current_song;
    static boolean FLAG_PLAY_NEXT_SONG;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Now playing", Toast.LENGTH_LONG).show();
        CONTEXT = this;

        //TODO: START PLAYING SONGS
        //TODO: OnInterrupt, update the probability model
        //interrupt includes, finish of a song & song interrupted in middle.
        copy_song_list_from_first_page();
        play_next_song();
        return START_STICKY;
    }

    // copies the song from app to service
    void copy_song_list_from_first_page() {
        if (SONGS_IN_PHONE == null) {
            SONGS_IN_PHONE = new HashMap<Long, Song>();
            for (Song song : FirstPage.SONGS_IN_PHONE.values()) {
                Song song_copy = copy_song(song);
                this.SONGS_IN_PHONE.put(song_copy.id, song_copy);
            }
        } else {
            //TODO: figure out what to do if songs are already loaded
        }
    }

    //copying the song object
    Song copy_song(Song song) {
        Song copied = new Song(song.id, song.name, song.time_duration, song.artist_name, song.full_path, song.trade_secret);
        int[] trade_secret = new int[42];
        for (int i = 0; i < 42; i++) {
            trade_secret[i] = song.trade_secret[i];
        }
        copied.trade_secret = trade_secret;
        return copied;
    }

    void play_next_song() {
        FLAG_PLAY_NEXT_SONG = false;
        //playing one song
        current_song = null;
        try {
            //TODO: get the next song in the list
            
            String song_location = "";
            current_song = MediaPlayer.create(SongPlayerService.CONTEXT, Uri.parse(song_location));
            current_song.prepare();
            int song_duration = current_song.getDuration();
            start_timer(song_duration);
            FirstPage._("Song full name:" + song_location);
            FirstPage._("Song Duration:" + song_duration);
            current_song.start();
        } catch (IOException e) {
            FirstPage._("Error while playing the song");
            e.printStackTrace();
        }


        //THIS GIVES THE TIME FOR WHICH THE SONG IS GOING TO BE PLAYED FOR.
        //TODO: get this
        /*
        * String filePath = Environment.getExternalStorageDirectory()+"/yourfolderNAme/yopurfile.mp3";
        *   mediaPlayer = new  MediaPlayer();
        *   mediaPlayer.setDataSource(filePath);
        *   mediaPlayer.prepare();
        *   mediaPlayer.start()
        * */
    }


    //param time limit is in seconds (HOPEFULLY)
    void start_timer(int time_limit) {
        time_limit -= 4;//cutting down the song duration by 4 seconds
        Long start_time = System.currentTimeMillis();
        //TODO: check if time_limit is in seconds
        Long interval_in_millis = Long.valueOf(time_limit) * 1000;
        while (true) {
            Long current_time = System.currentTimeMillis();
            if (current_time - start_time > interval_in_millis || FLAG_PLAY_NEXT_SONG) {
                break;
            } else {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Media Player Stopped", Toast.LENGTH_LONG).show();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
