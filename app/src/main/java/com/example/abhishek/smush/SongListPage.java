package com.example.abhishek.smush;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Abhishek on 02-06-2016.
 */
/*Displays the list of all songs*/
public class SongListPage  extends  AppCompatActivity {

    ListView lv;
    Context context;
    private static final String TAG = "Laststnd";

    Map<String, Song> SONGS_IN_PHONE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // sets the layout for this Activity
        setContentView(R.layout.song_list_page);
        getSupportActionBar().setTitle("Songs"); // to change the Action Bar title in SongListPage Activity

        context = this;
//        SongPlayerService.repopulate(); // fetch the song list
        if(SongPlayerService.SONGS_IN_PHONE==null)
        {
            Log.d(TAG,"No Songs in Phone or some error Occurred");

            Song tmp_song;
            SONGS_IN_PHONE = new HashMap<String, Song>();
            //Creating temporary songs
            tmp_song = new Song("11","Bolna",60,"Alia Bhatt","sudoroot/1",new int[0]);
            SONGS_IN_PHONE.put(tmp_song.id,tmp_song);
            tmp_song = new Song("22","Dolna",120,"Rohit Bhatt","sudoroot/2",new int[0]);
            SONGS_IN_PHONE.put(tmp_song.id,tmp_song);
            tmp_song = new Song("33","Kholna",100,"Zingya Bhatt","sudoroot/3",new int[0]);
            SONGS_IN_PHONE.put(tmp_song.id,tmp_song);
            tmp_song = new Song("44","Colna",65,"Darshak Bhatt","sudoroot/4",new int[0]);
            SONGS_IN_PHONE.put(tmp_song.id,tmp_song);
            tmp_song = new Song("55","Zolna",30,"Gautam Bhatt","sudoroot/5",new int[0]);
            SONGS_IN_PHONE.put(tmp_song.id,tmp_song);
            tmp_song = new Song("66","Xolna",60,"Aliass Bhatt","sudoroot/6",new int[0]);
            SONGS_IN_PHONE.put(tmp_song.id,tmp_song);
            tmp_song = new Song("77","Aolna",60,"Aliadas Bhatt","sudoroot/7",new int[0]);
            SONGS_IN_PHONE.put(tmp_song.id,tmp_song);
            tmp_song = new Song("88","Holna",60,"Aliasdf Bhatt","sudoroot/8",new int[0]);
            SONGS_IN_PHONE.put(tmp_song.id,tmp_song);

        }
        else
        {
            Log.d(TAG,String.format(Locale.US,"Yes We got %d songs",SongPlayerService.SONGS_IN_PHONE.size()));
            SONGS_IN_PHONE = SongPlayerService.SONGS_IN_PHONE;
        }

        lv=(ListView) findViewById(R.id.listView);
        lv.setAdapter(new CustomAdapter(this, SONGS_IN_PHONE));

    }
}
