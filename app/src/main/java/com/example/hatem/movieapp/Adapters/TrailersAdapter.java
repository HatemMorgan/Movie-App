package com.example.hatem.movieapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hatem.movieapp.R;

import java.util.ArrayList;

/**
 * Created by hatem on 9/24/16.
 */
public class TrailersAdapter extends ArrayAdapter<String> {

    View convertView ;


    public TrailersAdapter(Context context, ArrayList<String> trailers) {
        super(context, 0, trailers);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String trailerText = "Trailer";
        String key = getItem(position) ;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.trailers_list_item, parent, false);
        }

        TextView textView_trailer = (TextView) convertView.findViewById(R.id.textView_trailer_title);
        textView_trailer.setText(trailerText+" "+(position++));

        ImageView imageView_play = (ImageView) convertView.findViewById(R.id.imageView_play);
        imageView_play.setContentDescription(key);




        return  convertView;
    }
}
