package com.example.hatem.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.hatem.movieapp.Fragments.Detailed_Fragment;
import com.example.hatem.movieapp.Fragments.MoviesFragment;
import com.example.hatem.movieapp.Utilities.MainUtitlity;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar ;
    boolean twopaneViewFlag ;
    boolean firstLunch ; //this boolean checks if the activity lunching is the first lunching or the sorting setting changed
    private String sortCriteria ;
    private MoviesFragment moviesFragment ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolBar();

        if(findViewById(R.id.DetialedMovies_container) != null){

            twopaneViewFlag = true ;



            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.DetialedMovies_container, new Detailed_Fragment())
                        .commit();


                firstLunch = true;

            }else{
                firstLunch = savedInstanceState.getBoolean("firstLunch");
                sortCriteria  = savedInstanceState.getString("sortCriteria");
            }
        }else {
            twopaneViewFlag = false;

        }

         moviesFragment =  ((MoviesFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_Movies));
        moviesFragment.setTwopaneViewFlag(twopaneViewFlag);
    }


    private void initToolBar (){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
//
//        if(!twopaneViewFlag) {
//
//
//            getSupportActionBar().setHomeButtonEnabled(true);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }

    }

    public void setFirstLunch(boolean firstLunch) {
        this.firstLunch = firstLunch;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("sortCriteria",sortCriteria);
        outState.putBoolean("firstLunch",false);
        super.onSaveInstanceState(outState);
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
                   moviesFragment.updateMovies();
                }else{
                    new Toast(this).makeText(this,"Check your internet connection and try again",Toast.LENGTH_SHORT).show();
                }
            }





    return true;
    }

    public void onItemSelect (Bundle bundle){
        if(twopaneViewFlag){
            Detailed_Fragment detailed_fragment = new Detailed_Fragment() ;
            detailed_fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.DetialedMovies_container , detailed_fragment).commit();
        }else {

            Intent intent = new Intent(this ,DetialedActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }



    /*
     * this method is used to set the detailed fragment to the first movie in the gridlist when first starting the app
     *  or changing the list due to settings change
     */

    public void onTwoPaneViewStarted (Bundle bundle){
            if(twopaneViewFlag && firstLunch){
                Detailed_Fragment detailed_fragment = new Detailed_Fragment() ;
                detailed_fragment.setArguments(bundle);
                firstLunch = false;
                getSupportFragmentManager().beginTransaction().replace(R.id.DetialedMovies_container , detailed_fragment).commit();


            }


    }

    public boolean isFirstLunch() {
        return firstLunch;
    }

    public void setSortCriteria(String sortCriteria) {
        this.sortCriteria = sortCriteria;
    }

    public String getSortCriteria() {

        return sortCriteria;
    }
}
