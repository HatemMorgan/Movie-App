package com.example.hatem.movieapp.ORM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class Page {

    private int page;
    private List<Movie> results = new ArrayList<Movie>();
    private int totalResults;
    private int totalPages;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     *     The page
     */
    public int getPageNumber() {
        return page;
    }



    /**
     *
     * @return
     *     The results
     */
    public List<Movie> getResults() {
        return results;
    }



    /**
     *
     * @return
     *     The totalResults
     */
    public int getTotalResults() {
        return totalResults;
    }



    /**
     *
     * @return
     *     The totalPages
     */
    public int getTotalPages() {
        return totalPages;
    }





}
