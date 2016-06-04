package com.example.abhishek.smush;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Abhishek on 02-06-2016.
 */
/* Adapter for the SongListView (Page) items*/
public class CustomAdapter extends BaseAdapter{

    private static final String TAG = "Laststnd";
//    String [] result;
    Context context;
//    int [] imageId;
    Map<String, Song> Songs_In_Phone;
    List<String> keys; // stores keys (names of songs) to return the position for custom adapter

    private static LayoutInflater inflater=null;
    public CustomAdapter(SongListPage mainActivity, Map<String, Song> SONGS_IN_PHONE) {
        // TODO Auto-generated constructor stub
//        result=songNameList;
        context=mainActivity;
//        imageId=songImages;
        Songs_In_Phone = SONGS_IN_PHONE;
         keys = getSongList(Songs_In_Phone);
//        keys = new ArrayList<String>(Songs_In_Phone.keySet());
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return Songs_In_Phone.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return Songs_In_Phone.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public String _getItemId(int position) {
        // TODO Auto-generated method stub
        return keys.get(position);
    }

    private ArrayList<String> getSongList(Map <String,Song> Songs_In_Phone){
        //Generates the Song List According to piority
        ArrayList <String> _keys;

        //Do your Priority Listing
        _keys = new ArrayList<String>(Songs_In_Phone.keySet());
        return _keys;
    }

    private String getDurationFormat(int duration){
        // returns duration formatted to be displayed as in song list
        // takes duration is Seconds
        duration = duration/1000; // converting duration in seconds
        String dur= "";
//        dur+=Integer.toString(duration%60);
//        duration = duration/60;
//        dur=Integer.toString(duration)+":"+dur;
        dur = String.format(Locale.US,"%02d:%02d",duration/60,duration%60);

        return dur;
    }

    public class Holder
    {
        TextView song_name,song_artist,song_duration;
        ImageView song_img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.song_list_item_layout, null);
        holder.song_name=(TextView) rowView.findViewById(R.id.textViewSongName);
        holder.song_artist=(TextView) rowView.findViewById(R.id.textViewSongArtist);
        holder.song_duration=(TextView) rowView.findViewById(R.id.textViewSongDuration);
        holder.song_img=(ImageView) rowView.findViewById(R.id.imageViewSongImage);

//        Log.d(TAG,"position: "+position);
        final Song song = Songs_In_Phone.get(_getItemId(position));
        final String name = song.name;
        holder.song_name.setText(name);
        holder.song_artist.setText(song.artist_name);
        holder.song_duration.setText(getDurationFormat(song.time_duration));
//        Log.d(TAG,"Song Duration: "+Integer.toString(song.time_duration));
        //Setting the Song Name Image
        String image_name = name.charAt(0)+""+name.charAt(0);
        image_name = image_name.toLowerCase();
//        Log.d(TAG,"Drawable Image Name = "+image_name);
        if( (name.charAt(0)>='A' && name.charAt(0)<='Z' ) || (name.charAt(0)>='a' && name.charAt(0)<='z') )
            holder.song_img.setImageResource(this.context.getResources().getIdentifier(image_name,"drawable",this.context.getPackageName()));
        else
            holder.song_img.setImageResource(R.drawable.nn);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.d(TAG,"You clicked a rowView");
                Toast.makeText(context, "You Clicked "+name, Toast.LENGTH_SHORT).show();

                //Start your activity to play this song
//                Intent playSong = new Intent(context,SongPage.class);
//                playSong.putExtra("id",song.id);
//                context.startActivity(playSong);

                //Play the song
//                try {
//                    Log.d(TAG,"TRYing to play");
//                    MediaPlayer current_song = new MediaPlayer();
//                    current_song.setDataSource(song.full_path);
//                    current_song.prepare();
//                    current_song.start();
//                    Log.d(TAG,"Play started");
//                }
//                catch (IOException e) {
//                    FirstPage._("Error while playing the song");
//                    e.printStackTrace();
//                } catch (NullPointerException e) {
//                    FirstPage._("No songs to play");
//                }
            }
        });
        return rowView;
    }


}
