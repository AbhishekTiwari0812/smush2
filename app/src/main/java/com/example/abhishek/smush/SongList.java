package com.example.abhishek.smush;


/**
 * Edited by protino
 */


import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import java.util.* ;
import java.io.*;
import android.util.*;
import android.os.*;


public class SongList extends AsyncTask<Void,Void,Void> {

    private List<String> mediaList = new ArrayList<String>();
    private String[] STAR = { "*" };

    @Override
	protected Void doInBackground(Void... params) {
        listAllSongs();
        return null;
    }

    // FIXME
    //Nothing at present but progress bar can be established here...
    @Override
	protected void onPreExecute( ) {
        super.onPreExecute( );
    }

    //Display when completed scanning the entire disk
    @Override
	protected void onPostExecute(Void aVoid) {
        //showDialog( "Completed Scanning" );
        super.onPostExecute(aVoid);
    }

    //Called to update the progress bar accordingly
    //@Override
    protected void onProgressUpdate(Integer... progress) {
        //setProgressPercent(progress[0]);
    }

    //To be called from the doInBackground function
    protected void listAllSongs( ){

        String externalStoragePath = Environment.getExternalStorageDirectory()
	    .getAbsolutePath();
        File targetDir = new File(externalStoragePath);
        File[] mediaFiles = targetDir.listFiles();
        scanFiles( mediaFiles );
    }

    // Recursively scan all files and directories
    public void scanFiles(File[] scanFiles) {
        if (scanFiles != null) {
            for (File file : scanFiles) {
                if(mediaList.size() > 4){
                    Log.d("Not a valid Dir",file.getAbsolutePath());
                    return;
                }
                if (file.isDirectory()) {
		    Log.d(" scaned Directory ", file.getAbsolutePath());
                    scanFiles(file.listFiles());

                } else {
                    addToMediaList(file);
                }
            }
        } else {

        }
    }

    //once scanned add to media list the found music file
    private void addToMediaList(File file) {
        if (file != null) {
            String path = file.getAbsolutePath();
            int index = path.lastIndexOf(".");
            String extn = path.substring(index + 1, path.length());
            if (extn.equalsIgnoreCase("mp4") || extn.equalsIgnoreCase("mp3")) {// ||
                Log.d(" scanned File ::: ", file.getAbsolutePath()
		      + "  file.getPath( )  " + file.getPath());// extn.equalsIgnoreCase("mp3"))
                mediaList.add(path);
            }
        }
    }

}
