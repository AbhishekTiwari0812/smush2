package com.example.abhishek.smush;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Abhishek on 02-06-2016.
 */
/* Adapter for the SongListView (Page) items*/
public class CustomAdapterSongInfo extends BaseAdapter{

    private static final String TAG = "Laststnd";
    Context context;
    Song song;
    int interval;
    List <String> Days = Arrays.asList("Sun","Mon","Tue","Wed","Thu","Fri","Sat");

    private static LayoutInflater inflater=null;
    public CustomAdapterSongInfo(SongInfo mainActivity, Song song) {
        // TODO Auto-generated constructor stub
        context=mainActivity;
        if(song.trade_secret==null)
        {
            // initialize all 42 elements to 0;
            Log.d(TAG,"Trade Secret is Null");
            song.trade_secret = new int[42];
            Arrays.fill(song.trade_secret,0);
        }
        interval = song.trade_secret.length/Days.size();
        this.song = song;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return Days.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return Days.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    private String getDurationFormat(int duration){
        // returns duration formatted to be displayed as in song list
        // takes duration is Seconds
        duration = duration/1000; // converting duration in seconds
        String dur= "";
        dur = String.format(Locale.US,"%02d:%02d",duration/60,duration%60);

        return dur;
    }

    public class Holder
    {
        TextView day_name;
        EditText priority[] = new EditText[interval];
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.song_info_item_layout, null);
        holder.day_name = (TextView) rowView.findViewById(R.id.textViewDay);
        holder.priority[0] = (EditText) rowView.findViewById(R.id.textViewPriority1);
        holder.priority[1] = (EditText) rowView.findViewById(R.id.textViewPriority2);
        holder.priority[2] = (EditText) rowView.findViewById(R.id.textViewPriority3);
        holder.priority[3] = (EditText) rowView.findViewById(R.id.textViewPriority4);
        holder.priority[4] = (EditText) rowView.findViewById(R.id.textViewPriority5);
        holder.priority[5] = (EditText) rowView.findViewById(R.id.textViewPriority6);

        Log.d(TAG,"position: "+position);
        holder.day_name.setText(getItem(position).toString());
        Log.d(TAG,"interval: "+interval+" s.ts: "+song.trade_secret[position*interval]);
        holder.priority[0].setText(Integer.toString(song.trade_secret[position*interval]));
        holder.priority[1].setText(Integer.toString(song.trade_secret[position*interval+1]));
        holder.priority[2].setText(Integer.toString(song.trade_secret[position*interval+2]));
        holder.priority[3].setText(Integer.toString(song.trade_secret[position*interval+3]));
        holder.priority[4].setText(Integer.toString(song.trade_secret[position*interval+4]));
        holder.priority[5].setText(Integer.toString(song.trade_secret[position*interval+5]));



//        Log.d(TAG,"Song Duration: "+Integer.toString(song.time_duration));

//        rowView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                Log.d(TAG,"You clicked a rowView");
//                Toast.makeText(context, "You Clicked "+getItem(position).toString(), Toast.LENGTH_SHORT).show();
//
//            }
//        });
        return rowView;
    }


}
