package com.example.hatem.movieapp.Fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.hatem.movieapp.Adapters.ReviewsAdapter;
import com.example.hatem.movieapp.ORM.Reviews;
import com.example.hatem.movieapp.R;
import com.example.hatem.movieapp.RequestQueueSingelton;
import com.example.hatem.movieapp.Review_Activity;
import com.example.hatem.movieapp.Utilities.MainUtitlity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * A simple {@link Fragment} subclass.
 */
public class Reviews_Fragment extends Fragment {

    ImageView imageView_moviePoster;
    TextView textView_movieTitle;
    TextView textView_movieReleaseYear;
    TextView textView_noReviews;
    TextView textView_Overview ;
    ListView listView_review_list;

    String movieID;
    ArrayList<Reviews> reviewsArrayList;
   final ArrayList<Hashtable<String, String>> htblList  = new ArrayList<Hashtable<String, String>>();


    public Reviews_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reviewsArrayList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_reviews_, container, false);

        imageView_moviePoster = (ImageView) rootView.findViewById(R.id.imageView_Review_moviePoster);
        textView_movieTitle = (TextView) rootView.findViewById(R.id.textView_Review_movieTitle);
        textView_movieReleaseYear = (TextView) rootView.findViewById(R.id.textView_review_movieYeat);
        listView_review_list = (ListView) rootView.findViewById(R.id.listView_Reviews_list);
        textView_noReviews = (TextView) rootView.findViewById(R.id.textView_NoReviews);

        textView_Overview = (TextView) rootView.findViewById(R.id.textView_Review_overview);


        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        updateReviews();



    }

    public void updateReviews(){
        if(MainUtitlity.isOnline(getActivity())) {

            populateview();

            if (((Review_Activity) getActivity()).getDataToBeRetained() == null) {
                populateReviewsList();
            } else {
                ArrayList<Hashtable<String, String>> reviewsList = ((Review_Activity) getActivity()).getDataToBeRetained();

                for (Hashtable<String, String> htblResults : reviewsList) {
                    String authorName = htblResults.get("authorName");
                    String reviewContent = htblResults.get("content");
                    String authorID = htblResults.get("authorID");
                    String author_profile_path = htblResults.get("imagePath");
                    String reviewid = htblResults.get("reviewID");


                    Reviews newReview = new Reviews(reviewid, reviewContent, authorName, authorID, author_profile_path);

                    reviewsArrayList.add(newReview);
                }

                ReviewsAdapter reviewsAdapter = new ReviewsAdapter(getActivity(), reviewsArrayList);
                listView_review_list.setAdapter(reviewsAdapter);
                MainUtitlity.setListViewHeightBasedOnChildren(listView_review_list);



            }
        }else{
            new Toast(getActivity()).makeText(getActivity(),"Check your internet connection and try again",Toast.LENGTH_SHORT).show();
        }

    }




    public void populateview() {

        Bundle bundle = getActivity().getIntent().getExtras();

        movieID = bundle.getString("movieID");
        String movieTitle = bundle.getString("title");
        String movieReleaseYear = bundle.getString("year");
        String moviePosterPath = bundle.getString("moviePosterPath");


        Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w500/" + moviePosterPath).into(imageView_moviePoster);

        textView_movieTitle.setText(movieTitle);


        if(textView_Overview != null){
            String movieOverview = bundle.getString("overview");
            textView_Overview.setText(movieOverview);
            textView_movieReleaseYear.setText("( "+ movieReleaseYear+" )");
        }else{
            textView_movieReleaseYear.setText(movieReleaseYear);
        }
    }

    public void populateReviewsList() {
        String getMoviesURL = "http://api.themoviedb.org/3/movie/";

        Uri buildUri = Uri.parse(getMoviesURL + movieID + "/reviews")
                .buildUpon()
                .appendQueryParameter("api_key", getString(R.string.api_key))
                .build();


        final ArrayList<String> reviewsIDsList = new ArrayList<String>();


        JsonObjectRequest allReviewsRequest = new JsonObjectRequest(Request.Method.GET, buildUri.toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray reviewsArray = response.getJSONArray("results");
                            for (int i = 0; i < reviewsArray.length(); i++) {
                                JSONObject Review = reviewsArray.getJSONObject(i);
                                String reviewID = Review.getString("id");
                                reviewsIDsList.add(reviewID);
                            }
                            final Hashtable<String, String> htblReviews = new Hashtable();
                            String getReviewURL = "http://api.themoviedb.org/3/review/";

                            if(reviewsIDsList.size() != 0) {
                                String reviewID = reviewsIDsList.get(0);
                                htblReviews.put("reviewID", reviewID);

                                int counterArray[] = {0};


                                getSingleReview(counterArray, reviewsIDsList);


                            }else{
                                textView_noReviews.setText("No Reviews ");
                            }
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

        RequestQueueSingelton.getmInstance(getActivity().getApplicationContext()).addToRequestQueue(allReviewsRequest);


    }


    public void getSingleReview(final int[] counter, final ArrayList<String> reviewIDList) {
        final Hashtable<String, String> htblReviews = new Hashtable();




            if (counter[0] == reviewIDList.size()) {

                // set object to be retained when the fragment and activity destroyed

                ((Review_Activity)getActivity()).setDataToBeRetained(htblList);

                for (Hashtable<String, String> htblResults : htblList) {
                    String authorName = htblResults.get("authorName");
                    String reviewContent = htblResults.get("content");
                    String authorID = htblResults.get("authorID");
                    String author_profile_path = htblResults.get("imagePath");
                    String reviewid = htblResults.get("reviewID");


                    Reviews newReview = new Reviews(reviewid, reviewContent, authorName, authorID, author_profile_path);

                    reviewsArrayList.add(newReview);

                }


                ReviewsAdapter reviewsAdapter = new ReviewsAdapter(getActivity(), reviewsArrayList);
                listView_review_list.setAdapter(reviewsAdapter);
                MainUtitlity.setListViewHeightBasedOnChildren(listView_review_list);

                return ;
            } else {

                String getReviewURL = "http://api.themoviedb.org/3/review/";

                String reviewID = reviewIDList.get(counter[0]);
                htblReviews.put("reviewID", reviewID);

                Uri buildUri = Uri.parse(getReviewURL + reviewID)
                        .buildUpon()
                        .appendQueryParameter("api_key", getString(R.string.api_key))
                        .build();


                JsonObjectRequest singleReviewRequest = new JsonObjectRequest(Request.Method.GET, buildUri.toString(), null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                try {

                                    htblReviews.put("content", response.getString("content"));
                                    htblReviews.put("authorID", response.getString("id"));
                                    htblReviews.put("authorName", response.getString("author"));


                                    String getAuthorImageUri = "http://api.themoviedb.org/3/person/";
                                    Uri buildUri = Uri.parse(getAuthorImageUri + htblReviews.get("authorID") + "/images")
                                            .buildUpon()
                                            .appendQueryParameter("api_key", getString(R.string.api_key))
                                            .build();


                                    JsonObjectRequest AuthorProfileRequest = new JsonObjectRequest(Request.Method.GET, buildUri.toString(), null,
                                            new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject response) {

                                                    try {
                                                        JSONArray profilesArray = response.getJSONArray("profiles");

                                                        if(profilesArray.length() != 0) {

                                                            JSONObject imageObject = profilesArray.getJSONObject(0);
                                                            String imagePath = imageObject.getString("file_path");
                                                            htblReviews.put("imagePath", imagePath);
                                                            htblList.add(htblReviews);
                                                            counter[0] += 1;
                                                            getSingleReview(counter, reviewIDList);
                                                        }else{

                                                            htblReviews.put("imagePath", "Null");
                                                            htblList.add(htblReviews);
                                                            counter[0] += 1;

                                                            getSingleReview(counter, reviewIDList);
                                                        }

                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }


                                                }
                                            }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                            htblReviews.put("imagePath", "Null");
                                            htblList.add(htblReviews);
                                            counter[0] += 1;

                                            getSingleReview(counter, reviewIDList);

                                        }
                                    });


                                    RequestQueueSingelton.getmInstance(getActivity().getApplicationContext()).addToRequestQueue(AuthorProfileRequest);


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

                RequestQueueSingelton.getmInstance(getActivity().getApplicationContext()).addToRequestQueue(singleReviewRequest);
            }


        }
    }








