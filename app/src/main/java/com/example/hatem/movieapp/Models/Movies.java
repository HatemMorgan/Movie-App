package com.example.hatem.movieapp.Models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Movies")
public class Movies extends Model {

    @Column(name = "movieID" , index = true)
    public String movieID ;

    @Column(name = "movieTitle")
    public String movieTitle ;

    @Column(name = "poster_path")
    public  String  poster_path ;

    @Column(name = "overview")
    public String overview ;

    @Column(name = "rating")
    public String rating ;

    @Column(name = "release_date")
    public String release_date;

    public Movies(){
        super();
    }

    public Movies(String movieID, String movieTitle, String overview, String rating, String poster_path, String release_date) {
        this.movieID = movieID;
        this.movieTitle = movieTitle;
        this.overview = overview;
        this.rating = rating;
        this.poster_path = poster_path;
        this.release_date = release_date;
    }
}
