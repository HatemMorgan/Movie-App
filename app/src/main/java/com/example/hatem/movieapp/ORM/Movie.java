package com.example.hatem.movieapp.ORM;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Movie {



    private String poster_path;
    private boolean adult;
    private String overview;
    private String release_date;
    private List<Integer> genreIds = new ArrayList<Integer>();
    private int id;
    private String originalTitle;
    private String originalLanguage;
    private String title;
    private String backdrop_path;
    private double popularity;
    private int voteCount;
    private boolean video;
    private double vote_average;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     *     The posterPath
     */
    public String getPoster_path() {
        return poster_path;
    }


    /**
     *
     * @return
     *     The adult
     */
    public boolean hasAdult() {
        return adult;
    }



    /**
     *
     * @return
     *     The overview
     */
    public String getOverview() {
        return overview;
    }


    /**
     *
     * @return
     *     The releaseDate
     */
    public String getReleaseDate() {
        return release_date;
    }


    /**
     *
     * @return
     *     The genreIds
     */
    public List<Integer> getGenreIds() {
        return genreIds;
    }


    /**
     *
     * @return
     *     The id
     */
    public int getId() {
        return id;
    }



    /**
     *
     * @return
     *     The originalTitle
     */
    public String getOriginalTitle() {
        return originalTitle;
    }



    /**
     *
     * @return
     *     The originalLanguage
     */
    public String getOriginalLanguage() {
        return originalLanguage;
    }



    /**
     *
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }



    /**
     *
     * @return
     *     The backdropPath
     */
    public String getBackdrop_path() {
        return backdrop_path;
    }



    /**
     *
     * @return
     *     The popularity
     */
    public double getPopularity() {
        return popularity;
    }



    /**
     *
     * @return
     *     The voteCount
     */
    public int getVoteCount() {
        return voteCount;
    }



    /**
     *
     * @return
     *     The video
     */
    public boolean hasVideo() {
        return video;
    }



    /**
     *
     * @return
     *     The voteAverage
     */
    public double getVoteAverage() {
        return vote_average;
    }






}
