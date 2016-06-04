package com.example.abhishek.smush;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Stack;

/**
 * Created by Abhishek on 02-06-2016.
 */
/*Displays the Music Player*/
//being edited by Abhishek
public class SongPage extends AppCompatActivity {
    TextView tv_previous_song;
    TextView tv_next_song;
    TextView tv_current_song_name;
    TextView tv_stop_current_song;
    String current_song_id;
    Stack<String> previous_song_list;
    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_player_layout);
        context = this;
        //initializing the page
        tv_previous_song = (TextView) findViewById(R.id.tv_previous_song);
        tv_next_song = (TextView) findViewById(R.id.tv_next_song);
        tv_stop_current_song = (TextView) findViewById(R.id.tv_stop_song);
        tv_current_song_name = (TextView) findViewById(R.id.tv_current_song_name);
        previous_song_list = new Stack<String>();


        tv_previous_song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (previous_song_list.empty()) {
                    //if current song is the first song being played
                    Toast.makeText(getApplicationContext(), "No previous song to be played", Toast.LENGTH_LONG).show();
                } else {
                    //get the previous song to be played
                    String next_song_id = previous_song_list.pop();
                    play_song(next_song_id);
                }
            }
        });
        tv_next_song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //remember the song id.
                previous_song_list.push(current_song_id);
                play_song(TradeSecret.get_next_song_id());
            }
        });
        tv_stop_current_song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    void play_song(String next_song_id) {
        current_song_id = next_song_id;
        SongPlayerService.FLAG_PLAY_NEXT_SONG = true;
        //TODO: add method to play next song.

        //TODO: if song is complete, play the next song and send the feedback

    }

    void notify_trade_secret(Long id) {
        //TODO: notify the trade secret values

    }
}
