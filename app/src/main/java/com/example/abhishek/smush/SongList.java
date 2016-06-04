package com.example.abhishek.smush;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SongList extends AsyncTask<Void,Void,Void> {

    private static final String TAG = "Laststnd";
    static final String MY_PREFERENCES = "SONG_LIST";
    SharedPreferences.Editor editor;
    private ProgressDialog dialog;
    private List<String> mediaList = new ArrayList<String>();
    private String[] STAR = {"*"};
    private MediaMetadataRetriever metaRetriver;
    private SharedPreferences sharedpreferences;
    @Override
    protected Void doInBackground(Void... params) {
        listAllSongs();
        return null;
    }

    @Override
    protected void onPreExecute( ) {
        super.onPreExecute();

        Intent intent = new Intent(FirstPage.FIRST_PAGE_CONTEXT, SongPlayerService.class);
        FirstPage.FIRST_PAGE_CONTEXT.startService(intent);

        sharedpreferences = FirstPage.FIRST_PAGE_CONTEXT.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        editor.clear();
        dialog = new ProgressDialog(FirstPage.FIRST_PAGE_CONTEXT);
        dialog.setMessage("Scanning files, please wait.");
        dialog.show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (dialog.isShowing()) {
            dialog.dismiss();
        }

        Log.d(TAG,"Dialog Dismissed");
        editor.commit();
    }

    protected void listAllSongs() {
        String externalStoragePath = Environment.getExternalStorageDirectory()
                .getAbsolutePath();
        Log.d(TAG,"Internal Memeory Path: "+externalStoragePath);
        listAllCards(new File(externalStoragePath));
        if (new File("/storage/extSdCard/").exists()) {
            listAllCards(new File("/storage/extSdCard/"));
        }
        if (new File("/storage/sdcard1/").exists()) {
            listAllCards(new File("/storage/sdcard1/"));
        }
        if (new File("/storage/usbcard1/").exists()) {
            listAllCards(new File("/storage/usbcard1/"));
        }
        if (new File("/storage/sdcard0/").exists()) {
            listAllCards(new File("/storage/sdcard0/"));
        }

    }

    public void listAllCards(File base) {
        Log.d(TAG,"in ListAllCards: base:"+base.getPath());
        File[] mediaFiles = base.listFiles();
        scanFiles(mediaFiles);

    }
    public void scanFiles(File[] scanFiles) {
        if (scanFiles != null) {
            for (File file : scanFiles) {
                if (mediaList.size() > 4) {
                    return;
                }
                if (file.isDirectory() && file.getName().charAt(0)!='.') {
                    scanFiles(file.listFiles());
                } else {
                    addToMediaList(file);
                }
            }
        }
    }

    private void addToMediaList(File file) {
        if (file != null) {
            String path = file.getAbsolutePath();

            int index = path.lastIndexOf(".");
            String extn = path.substring(index + 1, path.length());
            if (extn.equalsIgnoreCase("mp3")) {// ||
//                Log.d(TAG,"PATH: "+path);
                metaRetriver = new MediaMetadataRetriever();
                metaRetriver.setDataSource(path);
                String name = "Unknown";
                String full_path = null;
                String artist_name = "No Artist";
                int duration = 0;
                try {
                    full_path = path;
//                    name = metaRetriver.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_TITLE);
                    name = file.getName();
                    artist_name = metaRetriver.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_ARTIST);
                    duration = Integer.valueOf(metaRetriver.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_DURATION));
                } finally {
                    if (duration > 6000) {              // Threshold time for a song must be > 1 minutes
                        add_song_in_map(path, name, artist_name, duration);
                    }
                }
            }
        }
    }


    void add_song_in_map(String song_path, String name, String artist_name, int duration) {
        Song song = new Song(song_path, name, duration, artist_name, song_path, null);
        editor.putString(song.id, song.name + "$" + song.time_duration + "$" + song.artist_name + "$" + song.full_path + "$");
//        Log.d(TAG,"Commited:"+sharedpreferences.getString(song.id,"NULL"));
        editor.commit();
    }
}