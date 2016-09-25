package com.example.hatem.movieapp;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by hatem on 19/08/16.
 */

public class RequestQueueSingelton {
    private static  RequestQueueSingelton mInstance = null;
    private  static Context mContext ;
    private RequestQueue mRequestQueue ;

    private  RequestQueueSingelton (Context context){
        // setting the instanse static variable to the Application context passed from getInstance method in order to get the application context from it
        mContext = context ;

        mRequestQueue = getmRequestQueue() ;
    }

    public static synchronized RequestQueueSingelton getmInstance (Context context){
        if(mInstance == null){
            mInstance = new RequestQueueSingelton(context);
        }

        return  mInstance;
    }

    public RequestQueue getmRequestQueue(){
        if(mRequestQueue == null){
            // intializing new request queue using volley library and use the application context mContext
            // getApplicationContext() is key, it make sure that no Activity context or BroadcastReceiver context passed instead of the application context
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }

        return  mRequestQueue ;
    }

    public <T> void addToRequestQueue(Request<T> newRequest){
        getmRequestQueue().add(newRequest);
    }

}
