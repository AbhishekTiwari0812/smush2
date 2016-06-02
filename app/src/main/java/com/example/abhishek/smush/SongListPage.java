package com.example.abhishek.smush;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Abhishek on 02-06-2016.
 */
/*Displays the list of all songs*/
public class SongListPage  extends  AppCompatActivity {

    ListView lv;
    Context context;

    ArrayList songName;
    public static int [] songImages={};
    public static String [] songNameList={};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // sets the layout for this Activity
        setContentView(R.layout.song_list_page);

        context = this;

        lv=(ListView) findViewById(R.id.listView);
        lv.setAdapter(new CustomAdapter(this, songNameList,songImages));

    }
}
