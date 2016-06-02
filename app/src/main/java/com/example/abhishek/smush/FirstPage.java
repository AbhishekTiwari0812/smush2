
package com.example.abhishek.smush;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Abhishek on 02-06-2016.
 */
//being edited by Abhishek
public class FirstPage extends AppCompatActivity {
    static Map<Long, Song> SONGS_IN_PHONE = new HashMap<Long, Song>();      //keeps the list of all the songs in the phone
    Button button_start_player;
    Button button_open_song_list;
    boolean start_playing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button_start_player = (Button) findViewById(R.id.bt_start_playing);
        button_open_song_list = (Button) findViewById(R.id.bt_open_song_list);
        start_playing = true;
        //opens the music player app
        button_start_player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (start_playing) {
                    Intent intent = new Intent(getApplicationContext(), SongPage.class);
                    startActivity(intent);
                    button_start_player.setText("Stop Playing");
                    start_playing = false;
                } else {
                    //TODO: release all resources and close the app.
                }
            }
        });

        //opens the list of all songs
        button_open_song_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SongList.class);
                startActivity(intent);
            }
        });


    }
}
