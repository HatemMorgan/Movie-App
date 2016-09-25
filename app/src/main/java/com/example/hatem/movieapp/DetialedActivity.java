package com.example.hatem.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.hatem.movieapp.Fragments.Detailed_Fragment;
import com.example.hatem.movieapp.Utilities.MainUtitlity;

public class DetialedActivity extends AppCompatActivity {

    private String title ;
    private String poaster_path ;
    private String overview;
    private String rate ;
    private String release_date;
    private Toolbar toolbar;
    private Detailed_Fragment detailed_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detialed);
        initToolBar();

         detailed_fragment = new Detailed_Fragment() ;

        getSupportFragmentManager().beginTransaction().replace(R.id.DetialedMovies_container,detailed_fragment).commit();




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
                detailed_fragment.getTrailers(getIntent().getExtras().getString("id"));
            }else{
                new Toast(this).makeText(this,"Check your internet connection and try again",Toast.LENGTH_SHORT).show();
            }
        }

        return true;
    }


    private void initToolBar (){
        toolbar = (Toolbar) findViewById(R.id.DetailedTtoolbar);
        toolbar.setTitle(R.string.detailed_activity_name);

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

}
