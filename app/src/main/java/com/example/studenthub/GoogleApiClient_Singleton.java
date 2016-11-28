package com.example.studenthub;

import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by michaelspearing on 11/28/16.
 */

public class GoogleApiClient_Singleton {
    private static final String TAG = "GoogleApiClient_Singleton";
    private static GoogleApiClient_Singleton instance = null;
    private static GoogleApiClient mGoogleApiClient = null;

    protected GoogleApiClient_Singleton(){

    }

    public static GoogleApiClient_Singleton getInstance(GoogleApiClient aGAC){
        if(instance == null){
            instance = new GoogleApiClient_Singleton();
            if(mGoogleApiClient == null) {
                mGoogleApiClient = aGAC;
            }
        }
        return instance;
    }
    public static GoogleApiClient get_GoogleApiClient(){
        return mGoogleApiClient;
    }
}
