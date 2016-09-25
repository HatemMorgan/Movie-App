package com.example.hatem.movieapp.Fragments;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.hatem.movieapp.Adapters.TrailersAdapter;
import com.example.hatem.movieapp.Models.Movies;
import com.example.hatem.movieapp.R;
import com.example.hatem.movieapp.RequestQueueSingelton;
import com.example.hatem.movieapp.Review_Activity;
import com.example.hatem.movieapp.Utilities.DateUtility;
import com.example.hatem.movieapp.Utilities.MainUtitlity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Detailed_Fragment extends Fragment {

    ImageView movieIcon ;
    TextView textView_Year ;
    TextView textView_Daymonth ;
    TextView textView_overView ;
    TextView textView_rate ;
    TextView textView_title;
    ListView trailers_listView ;
    Button reviews_button ;
    CheckBox btn_favourite ;
    Context context ;

    String moiveID =null ;
    String movieTitle;
    String movieReleaseYear;
    String moviePosterPath ;
    String overview ;
    String rate ;
    String release_date;


    public Detailed_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity() ;



        Bundle intentBundle = getActivity().getIntent().getExtras();

        if(intentBundle != null) {

            movieTitle = intentBundle.getString("title");
            moviePosterPath = intentBundle.getString("poster_path");
            overview = intentBundle.getString("overview");
            rate = intentBundle.getString("rating");
            release_date = intentBundle.getString("release_date");
            moiveID = intentBundle.getString("id");
        }

            Bundle bundle = getArguments();
            if(bundle !=null) {
                movieTitle = bundle.getString("title");
                moviePosterPath = bundle.getString("poster_path");
                overview = bundle.getString("overview");
                rate = bundle.getString("rating");
                release_date = bundle.getString("release_date");
                moiveID = bundle.getString("id");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_detailed, container, false);

        movieIcon = (ImageView) rootView.findViewById(R.id.imageView_poaster);
        textView_Year = (TextView) rootView.findViewById(R.id.textView_year);
        textView_Daymonth = (TextView) rootView.findViewById(R.id.textView_date);
        textView_overView = (TextView) rootView.findViewById(R.id.textView_overview);
        textView_rate = (TextView) rootView.findViewById(R.id.textView_rate);
        textView_title = (TextView) rootView.findViewById(R.id.textView_title);

        trailers_listView = (ListView) rootView.findViewById(R.id.listView_trailers);

        trailers_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    ImageView imageView_play = (ImageView) view.findViewById(R.id.imageView_play);
                    String key =  imageView_play.getContentDescription().toString();

                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + key));
                try {
                    startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    startActivity(webIntent);
                }

            }
        });


        reviews_button = (Button) rootView.findViewById(R.id.btn_Reviews);
        reviews_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle review_bundle = new Bundle();
                review_bundle.putString("movieID",moiveID);
                review_bundle.putString("title",movieTitle);
                review_bundle.putString("year",movieReleaseYear);
                review_bundle.putString("moviePosterPath",moviePosterPath);
                review_bundle.putString("overview",overview);

                Intent intent = new Intent(getActivity(), Review_Activity.class);
                intent.putExtras(review_bundle);
                startActivity(intent);
            }
        });

        btn_favourite = (CheckBox) rootView.findViewById(R.id.btn_favourite);

        if(moiveID != null) {
            Movies checkIfFavouriteMovie = new Select().from(Movies.class).where("movieID = ?", moiveID).executeSingle();

            if (checkIfFavouriteMovie != null) {
                btn_favourite.setChecked(true);
            }
        }
        
        btn_favourite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if(isChecked) {

                    Movies newMovies = new Movies(moiveID, movieTitle, overview, rate, moviePosterPath, release_date);
                    newMovies.save();
                }else{
                    new Delete().from(Movies.class).where("movieID = ?", moiveID).execute();
                }
            }
        });


        return rootView ;
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        populateMovieViews();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        populateMovieViews();

    }




    private void populateMovieViews (){

        if(moiveID != null) {

            if(getActivity().findViewById(R.id.textView_testLayout) !=null) {
                Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w500" + moviePosterPath).into(movieIcon);
            }else{
                Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w342" + moviePosterPath).into(movieIcon);
            }

            textView_title.setText(movieTitle);

            textView_overView.setText(overview);


            textView_rate.setText(rate+"/10");

            movieReleaseYear = DateUtility.getFormattedyear(release_date);
            String dayMonth = DateUtility.getFormattedMonthDay(release_date);

            textView_Year.setText(movieReleaseYear);

            textView_Daymonth.setText(dayMonth);

            if(MainUtitlity.isOnline(getActivity())){
                getTrailers(moiveID);
            }else{
                new Toast(getActivity()).makeText(getActivity(),"Check your internet connection and try again",Toast.LENGTH_SHORT).show();
            }

        }
    }


    public void getTrailers ( String id ){

        String getMoviesURL = "http://api.themoviedb.org/3/movie/";
        final ArrayList<String> trailersList  = new ArrayList<String>();

        Uri buildUri = Uri.parse(getMoviesURL+id+"/videos")
                        .buildUpon()
                        .appendQueryParameter("api_key",getString(R.string.api_key))
                        .build();


        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,buildUri.toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray trailersArray =  response.getJSONArray("results");

                         for(int i =0 ; i<trailersArray.length() ; i++){
                             JSONObject trailer = trailersArray.getJSONObject(i);
                             String videoKey = trailer.getString("key");
                             trailersList.add(videoKey);

                         }

                            TrailersAdapter trailersAdapter = new TrailersAdapter(context,trailersList);
                            trailers_listView.setAdapter(trailersAdapter);

                            MainUtitlity.setListViewHeightBasedOnChildren(trailers_listView);

                            
                            
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("Response Error", error.getMessage());

            }
        });


        RequestQueueSingelton.getmInstance(context.getApplicationContext()).addToRequestQueue(req);





    }
}
