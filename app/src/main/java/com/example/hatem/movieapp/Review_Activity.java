package com.example.hatem.movieapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.hatem.movieapp.Fragments.RetainedFragment;
import com.example.hatem.movieapp.Fragments.Reviews_Fragment;
import com.example.hatem.movieapp.Utilities.MainUtitlity;

import java.util.ArrayList;
import java.util.Hashtable;

public class Review_Activity extends AppCompatActivity {

    private  Toolbar toolbar;
    private  ArrayList<Hashtable<String,String>> dataToBeRetained ;
    private RetainedFragment dataFragment ;
    private Reviews_Fragment reviews_fragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_);
        initToolBar();

        dataFragment = (RetainedFragment) getFragmentManager().findFragmentByTag("data");

        if(dataFragment == null){
            dataFragment = new RetainedFragment() ;
            getFragmentManager().beginTransaction().add(dataFragment,"data").commit() ;
            dataToBeRetained = null;
        }else{
            dataToBeRetained = dataFragment.getRetainedData();
        }



         reviews_fragment = new Reviews_Fragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.Reviews_container, reviews_fragment).commit();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        dataFragment.setRetainedData(dataToBeRetained);
    }



    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.reviews_activity_name);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater() ;
        menuInflater.inflate(R.menu.menu , menu);




        return  true ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if( id == R.id.action_settings){
            startActivity(new Intent(this , SettingsActivity.class));
            finish();
        }

        if(id == R.id.action_refresh) {
            if(MainUtitlity.isOnline(this)){
               reviews_fragment.updateReviews();
            }else{
                new Toast(this).makeText(this,"Check your internet connection and try again",Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }

    public ArrayList<Hashtable<String, String>> getDataToBeRetained() {
        return dataToBeRetained;
    }

    public void setDataToBeRetained(ArrayList<Hashtable<String, String>> dataToBeRetained) {
        this.dataToBeRetained = dataToBeRetained;
    }
}

