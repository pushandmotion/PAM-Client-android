package com.pushandmotion.pamservices;

import com.pushandmotion.pamservices.core.PAMClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heart on 8/7/2017 AD.
 */

public class PAM {
    private static PAMClient pam;
    private static List<String> pendingList;
    private static boolean ready = false;

    public static void init(String siteURL, String apiKey){

        pendingList = new ArrayList<>();

        pam = new PAMClient();
        pam.setSiteURL(siteURL);
        pam.setApiKey(apiKey);

        pam.setListener(new PAMClient.PAMClientListener(){
            @Override
            public void onStart(){
                ready = true;

                while( pendingList.size() > 0) {
                    String track = pendingList.get(0);
                    trackPageView(track);
                    pendingList.remove(0);
                }

            }

            @Override
            public void onStartFail(String message) {

            }

        });

        pam.start();
    }

    public static boolean isReady() {
        return ready;
    }


    public static void trackPageView(String pageName){
        if(!ready) pendingList.add(pageName);
        pam.trackPageView(pageName);
    }

}
