package com.example.abhishek.smush;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;


public class SongPlayerService extends Service {
    static Map<String, Song> SONGS_IN_PHONE;
    static Context CONTEXT;
    static boolean FLAG_PLAY_NEXT_SONG;
    static MediaPlayer current_song;
    static String CURRENT_SONG_ID;

    static void start_playing() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                repopulate();
                while (true) {
                    FirstPage._("Trying to play songs");
                    Double percentage_played = play_next_song();
                    song_priority_update(CURRENT_SONG_ID, percentage_played);
                }
            }
        });
        t.start();
    }

    static void song_priority_update(String song_id, Double percentage) {
        TradeSecret.update_priority(song_id, percentage);
    }

    static Double play_next_song() {
        FLAG_PLAY_NEXT_SONG = false;
        current_song = null;
        final double[] percentage_played = new double[1];
        percentage_played[0] = 0.0d;
        try {
            FirstPage._("Getting next song");
            if (SONGS_IN_PHONE == null) {
                FirstPage._("No songs in the list");
                repopulate();
                FirstPage._("Repopulating");
            }
            CURRENT_SONG_ID = TradeSecret.get_next_song_id();
            FirstPage._("Got song id");
            String song_location = CURRENT_SONG_ID;
            FirstPage._("Got path:" + CURRENT_SONG_ID);

            current_song = MediaPlayer.create(FirstPage.FIRST_PAGE_CONTEXT, Uri.parse(song_location));
            //current_song.prepare();
            FirstPage._("Ready to play.");
            int song_duration = current_song.getDuration();
            FirstPage._("Song full name:" + song_location);
            FirstPage._("Song Duration:" + song_duration);
            FirstPage._("Now playing the song");
            current_song.start();
            //TODO: check song duration
            song_duration /= 1000;
            long duration_played = start_timer(song_duration);
            FirstPage._("SONG DURATION:" + song_duration);
            percentage_played[0] = Double.valueOf(duration_played) / Double.valueOf(song_duration);
            percentage_played[0] *= 100D;
        } catch (Exception e) {

            FirstPage._("Error while playing the song");
            e.printStackTrace();
        }
        return percentage_played[0];
    }

    //param time limit is in seconds (HOPEFULLY)
    static long start_timer(int time_limit) {
        time_limit -= 4;//cutting down the song duration by 4 seconds
        Long start_time = System.currentTimeMillis();
        //TODO: check if time_limit is in seconds
        Long interval_in_millis = Long.valueOf(time_limit) * 1000;
        while (true) {
            Long current_time = System.currentTimeMillis();
            if (current_time - start_time > interval_in_millis || FLAG_PLAY_NEXT_SONG) {
                return (current_time - start_time) / 1000;
            } else {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static void repopulate() {
        SharedPreferences sharedPreferences = SongPlayerService.CONTEXT.getSharedPreferences(SongList.MY_PREFERENCES, MODE_PRIVATE);
        Map<String, String> map = (Map<String, String>) sharedPreferences.getAll();
        if (SONGS_IN_PHONE != null)
            SONGS_IN_PHONE.clear();
        SONGS_IN_PHONE = new HashMap<String, Song>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            FirstPage._("Filling the songs");
            String id = entry.getKey();
            String[] temp = splitAtDollarDelimiter(entry.getValue());
            String name = temp[0];
            int time_duration;
            try {
                time_duration = Integer.valueOf(temp[1]);
            } catch (NumberFormatException e) {
                time_duration = 0;
            }
            String artist_name = temp[2];
            String full_path = temp[3];
            Song song = new Song(id, name, time_duration, artist_name, full_path, null);
            SONGS_IN_PHONE.put(id, song);
        }
    }

    public static String[] splitAtDollarDelimiter(String string) {
        String[] temp = string.split("\\$");
        String[] afterSplit = new String[4];
        for (int i = 0, j = 0; i < temp.length; i++) {
            if (temp[i].length() > 0) {
                afterSplit[j] = temp[i];
                j++;
            }
        }
        return afterSplit;
    }

    //copy the songs from Activity to service.
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        FirstPage._("onStartCommand");
        CONTEXT = this;
        Toast.makeText(this, "Service started", Toast.LENGTH_LONG).show();
        repopulate();
        if (SONGS_IN_PHONE == null)
            FirstPage._("SONGS IN PHONE ARE NULL");
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Media Player Stopped", Toast.LENGTH_LONG).show();
        FLAG_PLAY_NEXT_SONG = true;
        SONGS_IN_PHONE.clear();
        try {
            current_song.stop();
        } catch (NullPointerException e) {
        }
        SONGS_IN_PHONE = null;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
