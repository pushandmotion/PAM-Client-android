package com.pushandmotion.pamservices;

import com.pushandmotion.pamservices.core.PAMClient;

/**
 * Created by heart on 8/7/2017 AD.
 */

public class PAM {
    private static PAMClient pam;

    public static void init(String siteURL, String apiKey){
        pam = new PAMClient();
        pam.setSiteURL(siteURL);
        pam.setApiKey(apiKey);

        pam.setListener(new PAMClient.PAMClientListener(){
            @Override
            public void onReady(){

            }
        });

        pam.start();
    }

    public static void trackPageView(String pageName){

    }

}
