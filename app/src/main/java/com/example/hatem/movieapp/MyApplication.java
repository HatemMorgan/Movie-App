package com.example.hatem.movieapp;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.example.hatem.movieapp.Models.Movies;


public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Configuration dbConfiguration = new Configuration.Builder(this)
                .addModelClass(Movies.class)
                .setDatabaseName("MoviesDB.db")
                .create();
        ActiveAndroid.initialize(dbConfiguration);
    }


}
