package com.pushandmotion.pamservices;

import android.content.Context;

import com.pushandmotion.pamservices.core.PAMClient;
import com.pushandmotion.pamservices.data.TrackingData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heart on 8/7/2017 AD.
 */

public class PAM {
    private static PAMClient pam;
    private static List<TrackingData> pendingList;
    private static boolean ready = false;



    private static Context context;
    private static TrackingData.Builder defaultTrackingDataBuilder;

    public static TrackingData.Builder defaultTrackingData(){
        if(defaultTrackingDataBuilder == null){
            defaultTrackingDataBuilder = new TrackingData.Builder();
        }
        return defaultTrackingDataBuilder;
    }


    public static void init(Context c, String siteURL, String appID){

        defaultTrackingData()
                .setAppId(appID)
                .setCounter(0);


        context = c.getApplicationContext();

        pendingList = new ArrayList<>();

        pam = new PAMClient();
        pam.setSiteURL(siteURL);

        pam.setListener(new PAMClient.PAMClientListener(){
            @Override
            public void onStart(){
                ready = true;

                while( pendingList.size() > 0) {
                    TrackingData data = pendingList.get(0);
                    trackPageView(null,data);
                    pendingList.remove(0);
                }

            }

        });

        pam.start(appID);


    }

    public static Context getContext(){
        return context;
    }

    public static boolean isReady() {
        return ready;
    }


    public static void trackPageView(TrackingData data){
        if(!ready) pendingList.add(data);
        pam.trackPageView(data);
    }

    public static void trackPageView(String pageName, TrackingData data){
        data.page_title = pageName;
        pam.trackPageView(data);
    }

    public static void trackPageView(String pageName){
        TrackingData data = defaultTrackingData().clone().setPageTitle(pageName).build();
        pam.trackPageView(data);
    }

    public static TrackingData.Builder createTackingDataBuilder() {
        TrackingData.Builder builder = defaultTrackingData().clone();
        return builder;
    }


}
