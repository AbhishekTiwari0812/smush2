
package com.example.abhishek.smush;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class FirstPage extends AppCompatActivity {
    Button button_start_player;
    Button button_open_song_list;
    static Context FIRST_PAGE_CONTEXT;
    boolean start_playing;
    Button button_stop_playing;

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
        button_stop_playing = (Button) findViewById(R.id.bt_stop_playing);
        SongList songList = new SongList();
        songList.execute();

        button_start_player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _("Starting the song");
                button_start_player.setText("Start");
                SongPlayerService.start_playing();
            }

        });

        button_stop_playing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _("Stopping the song");
                button_start_player.setText("Stop");
                if (isMyServiceRunning(SongPlayerService.class)) {
                    Intent intent = new Intent(getApplicationContext(), SongPlayerService.class);
                    stopService(intent);
                    start_playing = false;
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
}
