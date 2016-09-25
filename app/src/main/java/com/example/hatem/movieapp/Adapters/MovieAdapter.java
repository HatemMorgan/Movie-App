package com.example.hatem.movieapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.hatem.movieapp.Models.Movies;
import com.example.hatem.movieapp.ORM.Movie;
import com.example.hatem.movieapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by hatem on 19/08/16.
 */
public class MovieAdapter extends BaseAdapter {

    private  List<Movie> responseMoviesList ;
    private  List<Movies> databaseMovieList ;
    private  Context context ;
    private LayoutInflater inflater ;
    private int mPosition ;

    public  MovieAdapter (Context context , List<Movie> responseMoviesList , List<Movies> databaseMovieList, int mPosition){
        this.context = context ;
        this.responseMoviesList = responseMoviesList ;
        this.databaseMovieList = databaseMovieList;
        this.mPosition = mPosition;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {

        if(responseMoviesList != null){
            return  responseMoviesList.size();
        }

        if(databaseMovieList != null){
            return  databaseMovieList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View rootView = inflater.inflate(R.layout.main_grid_list_item,null);
        ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView_icon);
        String imagePath = "" ;

        if(responseMoviesList !=null){
            Movie movie = responseMoviesList.get(i);


             imagePath = movie.getPoster_path();


        }

        if(databaseMovieList !=null){
            Movies movie = databaseMovieList.get(i);
            imagePath = movie.poster_path;
        }


        Picasso.with(context).load("http://image.tmdb.org/t/p/w300"+imagePath).into(imageView);

        if(i == mPosition){
            rootView.setBackgroundResource(R.color.light_blue);
        }




        return rootView ;

    }
}
