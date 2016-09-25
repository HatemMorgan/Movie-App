package com.example.hatem.movieapp.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hatem.movieapp.Adapters.MovieAdapter;
import com.example.hatem.movieapp.MainActivity;
import com.example.hatem.movieapp.Models.Movies;
import com.example.hatem.movieapp.ORM.Movie;
import com.example.hatem.movieapp.ORM.Page;
import com.example.hatem.movieapp.R;
import com.example.hatem.movieapp.RequestQueueSingelton;
import com.example.hatem.movieapp.Utilities.MainUtitlity;
import com.google.gson.Gson;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {

        GridView moviesGridView ;
        private  Context context  ;
        private SharedPreferences sharedPreferences ;
        private Page page;
        private List<Movies> databaseMoviesList;
        private boolean twopaneViewFlag ;

        private int mPosition = GridView.INVALID_POSITION;
        private static final String SELECTED_KEY = "selected_position";
        private  String RetainedsortCriteria ;




    public void setTwopaneViewFlag(boolean twoPaneViewCheck) {
        this.twopaneViewFlag = twoPaneViewCheck;
    }

    public boolean isTwopaneViewFlag() {

        return twopaneViewFlag;
    }



    public MoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_movies, container, false);

        moviesGridView = (GridView) rootView.findViewById(R.id.gridview_movies);

        moviesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Bundle bundle = new Bundle();

                String movieTitle = null;
                String poster_path = null;
                String overview = null;
                String rating = null;
                String release_date = null;
                String id = null;

                if(page != null) {

                    movieTitle = page.getResults().get(i).getTitle();
                    poster_path = page.getResults().get(i).getPoster_path();
                    overview = page.getResults().get(i).getOverview();
                    rating = page.getResults().get(i).getVoteAverage() + "" ;
                    release_date = page.getResults().get(i).getReleaseDate();
                    id = page.getResults().get(i).getId() + "" ;
                }

                if(databaseMoviesList !=null){
                    Movies movie = databaseMoviesList.get(i);
                    movieTitle =  movie.movieTitle;
                    poster_path = movie.poster_path;
                    overview = movie.overview;
                    rating = movie.rating;
                    release_date = movie.release_date;
                    id = movie.movieID;
                }



                bundle.putString("title", movieTitle);
                bundle.putString("poster_path",  poster_path);
                bundle.putString("overview",  overview);
                bundle.putString("rating", rating);
                bundle.putString("release_date", release_date);
                bundle.putString("id",  id);


                ((MainActivity)getActivity()).onItemSelect(bundle);

                mPosition = i ;

            }
        }) ;

        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            // The listview probably hasn't even been populated yet.  Actually perform the
            // swapout in onLoadFinished.

            mPosition = savedInstanceState.getInt(SELECTED_KEY);
        }


        return  rootView ;

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        if (mPosition != ListView.INVALID_POSITION) {
            outState.putInt(SELECTED_KEY, mPosition);
        }

        super.onSaveInstanceState(outState);

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        context = getActivity() ;
        databaseMoviesList = null;
        page = null;
        super.onCreate(savedInstanceState);



    }

    @Override
    public void onStart() {
        super.onStart();

        if(MainUtitlity.isOnline(getActivity())){
            updateMovies();
        }else{
           new Toast(getActivity()).makeText(getActivity(),"Check your internet connection and try again",Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        RetainedsortCriteria  = ((MainActivity)getActivity()).getSortCriteria();


        super.onActivityCreated(savedInstanceState);


    }

    public void updateMovies () {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        final String APPID_PARAM = "api_key";

      String  sortCriteria = sharedPreferences.getString(getString(R.string.pref_sort_list_key), getString(R.string.popular_sort_value));



        if(!sortCriteria.equals(RetainedsortCriteria)){
            ((MainActivity)getActivity()).setFirstLunch(true);
            ((MainActivity)getActivity()).setSortCriteria(sortCriteria);
        }

        if (!sortCriteria.equals("favorites")) {

            String getMoviesURL = "http://api.themoviedb.org/3/movie/";


            Uri buildUri = Uri.parse(getMoviesURL + sortCriteria)
                    .buildUpon()
                    .appendQueryParameter(APPID_PARAM, getString(R.string.api_key))
                    .build();

            StringRequest stringRequest = new StringRequest(Request.Method.GET, buildUri.toString(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {


                    Gson gson = new Gson();
                    page = gson.fromJson(response, Page.class);

                    if (mPosition != GridView.INVALID_POSITION && isTwopaneViewFlag()) {

                        MovieAdapter movieAdapter = new MovieAdapter(context, page.getResults(), null,mPosition);
                        moviesGridView.setAdapter(movieAdapter);
                    }else{
                        MovieAdapter movieAdapter = new MovieAdapter(context, page.getResults(), null,-1);
                        moviesGridView.setAdapter(movieAdapter);
                    }
                    /*
                     * checking if the mainActivity is first lunched or updated in order to auto select a movie on the start of the activity
                     */

                    if(isTwopaneViewFlag() && ((MainActivity)getActivity()).isFirstLunch() ){
                        Movie firstMovie = page.getResults().get(0);
                        Bundle bundle = new Bundle();


                        bundle.putString("title", firstMovie.getTitle());
                        bundle.putString("poster_path",  firstMovie.getPoster_path());
                        bundle.putString("overview",  firstMovie.getOverview());
                        bundle.putString("rating", firstMovie.getVoteAverage()+"");
                        bundle.putString("release_date", firstMovie.getReleaseDate());
                        bundle.putString("id",  firstMovie.getId()+"");

                        ((MainActivity)getActivity()).onTwoPaneViewStarted(bundle);

                    }



                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Log.e("Error Response", error.getStackTrace().toString());
                }
            });

            RequestQueueSingelton.getmInstance(getActivity().getApplicationContext()).addToRequestQueue(stringRequest);
            databaseMoviesList = null;
        } else {

             databaseMoviesList = new Select()
                                        .from(Movies.class)
                                        .orderBy("id DESC")
                                        .execute();
            if (mPosition != GridView.INVALID_POSITION && isTwopaneViewFlag()) {

                MovieAdapter movieAdapter = new MovieAdapter(context, null, databaseMoviesList,mPosition);
                moviesGridView.setAdapter(movieAdapter);
            }else{
                MovieAdapter movieAdapter = new MovieAdapter(context, null, databaseMoviesList,-1);
                moviesGridView.setAdapter(movieAdapter);
            }


            if(isTwopaneViewFlag() && ((MainActivity)getActivity()).isFirstLunch()){
                Movies firstMovie = databaseMoviesList.get(0);
                Bundle bundle = new Bundle();


                bundle.putString("title", firstMovie.movieTitle);
                bundle.putString("poster_path",  firstMovie.poster_path);
                bundle.putString("overview",  firstMovie.overview);
                bundle.putString("rating", firstMovie.rating);
                bundle.putString("release_date", firstMovie.release_date);
                bundle.putString("id",  firstMovie.movieID);

                ((MainActivity)getActivity()).onTwoPaneViewStarted(bundle);

            }

            page = null;

        }

        if (mPosition != GridView.INVALID_POSITION) {
            moviesGridView.smoothScrollToPosition(mPosition);
            moviesGridView.setSelection(mPosition);

        }
    }

}
