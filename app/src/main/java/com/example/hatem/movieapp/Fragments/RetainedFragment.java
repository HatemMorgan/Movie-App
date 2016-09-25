package com.example.hatem.movieapp.Fragments;

import android.app.Fragment;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by hatem on 9/25/16.
 */
public class RetainedFragment extends Fragment {

    private ArrayList<Hashtable<String,String>> retainedData ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public ArrayList<Hashtable<String, String>> getRetainedData() {
        return retainedData;
    }

    public void setRetainedData(ArrayList<Hashtable<String, String>> retainedData) {
        this.retainedData = retainedData;
    }
}
