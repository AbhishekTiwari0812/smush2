
package com.example.abhishek.smush;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Map;

/**
 * Created by Ali on 03-06-2016.
 */
//being edited by Ali
public class SongInfo extends AppCompatActivity {

    Button button_reset;
    ListView lv;
    Context context;
    private static final String TAG = "Laststnd";
    Song song;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_info_page);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        song = SongPlayerService.SONGS_IN_PHONE.get(id);
        if(song.artist_name == null || song.artist_name=="null")
        {
            Log.d(TAG,"SONG ARTIST is NULL");
            song.artist_name="";
        }
        Log.d(TAG,"Song Artist: "+song.artist_name);

        ((TextView) findViewById(R.id.textViewSongNameInfo)).setText(song.name);
        ((TextView) findViewById(R.id.textViewSongArtistInfo)).setText(song.artist_name);
        getSupportActionBar().setTitle("Song Info"); // to change the Action Bar title in SongInfo Activity

        lv=(ListView) findViewById(R.id.listViewDay);
        lv.setAdapter(new CustomAdapterSongInfo(this, song));

    }
}
