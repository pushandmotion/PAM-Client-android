package com.pushandmotion.pam;

import android.app.Application;

import com.pushandmotion.pamservices.PAM;

/**
 * Created by heart on 8/7/2017 AD.
 */

public class MainApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();

        final String siteURL = "http://private-bec2e8-pamclient.apiary-mock.com/event/";
        final String apiKEY = "xxxxx";

        PAM.init(siteURL,apiKEY);

    }
}
