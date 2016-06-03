
package com.example.abhishek.smush;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by Abhishek on 02-06-2016.
 */
//being edited by Abhishek
public class FirstPage extends AppCompatActivity {

    Button button_start_player;
    Button button_open_song_list;
    boolean start_playing;
    static Context FIRST_PAGE_CONTEXT;
    static void _(String str) {
        Log.d("ERROR::", "" + str);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FIRST_PAGE_CONTEXT = this;

        button_start_player = (Button) findViewById(R.id.bt_start_playing);
        button_open_song_list = (Button) findViewById(R.id.bt_open_song_list);

        SongList songList = new SongList();
        songList.execute();

        start_playing = true;

        button_start_player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (start_playing) {
                    button_start_player.setText("Stop Playing");
                    if (isMyServiceRunning(SongPlayerService.class)) {
                        SongPlayerService.start_playing();
                        start_playing = false;
                    } else {
                    }

                } else {
                    Intent intent = new Intent(getApplicationContext(), SongPlayerService.class);
                    if (isMyServiceRunning(SongPlayerService.class)) {
                        stopService(intent);
                    }
                    start_playing = true;
                    button_start_player.setText("Start Playing Again");
                }
            }
        });

        //opens the list of all songs

        button_open_song_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SongListPage.class);
                startActivity(intent);
            }
        });


    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    void start_service() {

        Intent intent = new Intent(FIRST_PAGE_CONTEXT, SongPlayerService.class);
        startService(intent);
        _("finished creating service");
    }
}
