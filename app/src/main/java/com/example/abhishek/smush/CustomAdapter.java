package com.example.abhishek.smush;

import android.util.Log;
import android.view.LayoutInflater;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Abhishek on 02-06-2016.
 */
/* Adapter for the SongListView (Page) items*/
public class CustomAdapter extends BaseAdapter{

    private static final String TAG = "Laststnd";
//    String [] result;
    Context context;
//    int [] imageId;
    Map<Long, Song> Songs_In_Phone;
    List<Long> keys; // stores keys (names of songs) to return the position for custom adapter

    private static LayoutInflater inflater=null;
    public CustomAdapter(SongListPage mainActivity, Map<Long, Song> SONGS_IN_PHONE) {
        // TODO Auto-generated constructor stub
//        result=songNameList;
        context=mainActivity;
//        imageId=songImages;
        Songs_In_Phone = SONGS_IN_PHONE;
        keys = new ArrayList<Long>(Songs_In_Phone.keySet());
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
        return keys.get(position);
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.song_list_item_layout, null);
        holder.tv=(TextView) rowView.findViewById(R.id.textView1);
        holder.img=(ImageView) rowView.findViewById(R.id.imageView1);

        Log.d(TAG,"position: "+position);
        final String name = Songs_In_Phone.get(getItemId(position)).name;
        holder.tv.setText(name);

        //Setting the Song Name Image
        String image_name = name.charAt(0)+""+name.charAt(0);
        image_name = image_name.toLowerCase();
        Log.d(TAG,"Drawable Image Name = "+image_name);
        holder.img.setImageResource(this.context.getResources().getIdentifier(image_name,"drawable",this.context.getPackageName()));
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked "+name, Toast.LENGTH_LONG).show();
            }
        });
        return rowView;
    }


}
