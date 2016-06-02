package com.example.abhishek.smush;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by aviji on 6/2/2016.
 */
public class TradeSecret {
    static final String trade_secret_pref_file = "trade_secret_pref";
    public static void update_priority(Long song_id,Double time_played) {
        Long timestamp = System.currentTimeMillis()/1000L;
        Integer current_time_slot = (int)((timestamp%604800L)/14400L);
        Song song = FirstPage.SONGS_IN_PHONE.get(song_id);
        Integer current_priority = song.trade_secret[current_time_slot];
        Integer new_priority = current_priority + (int)(time_played-0.5)*100;
        if (new_priority < 1) {
            new_priority = 1;
        }
        else if (new_priority > 10000) {
            new_priority = 10000;
        }
        song.trade_secret[current_time_slot] = new_priority;
        SharedPreferences pref_file = SongPage.context.getSharedPreferences(trade_secret_pref_file, Context.MODE_PRIVATE);
        SharedPreferences.Editor pref_file_editor = pref_file.edit();
        pref_file_editor.putInt(song_id.toString()+" "+current_time_slot.toString(),new_priority);
        pref_file_editor.commit();
    }

    public static long get_next_song_id() {
        Long timestamp = System.currentTimeMillis()/1000L;
        Integer current_time_slot = (int)((timestamp%604800L)/14400L);
        Set<Long> song_key_set = FirstPage.SONGS_IN_PHONE.keySet();
        while(song_key_set.isEmpty()) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            song_key_set = FirstPage.SONGS_IN_PHONE.keySet();
        }

        Long total_priority = 0L;
        for (Long song_key:
             song_key_set) {
            total_priority += FirstPage.SONGS_IN_PHONE.get(song_key).trade_secret[current_time_slot];
        }

        Long random_selector = (long)(Math.random() * total_priority + 1);
        for (Long song_key :
                song_key_set) {
            random_selector -= FirstPage.SONGS_IN_PHONE.get(song_key).trade_secret[current_time_slot];
            if (random_selector < 0) {
                return song_key;
            }
        }

        return 0;
    }
}
