package com.pushandmotion.pam;

import android.app.Application;

import com.pushandmotion.pamservices.PAM;
import com.pushandmotion.pamservices.core.PAMClient;

/**
 * Created by heart on 8/7/2017 AD.
 */

public class MainApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();

        final String siteURL = "http://private-bec2e8-pamclient.apiary-mock.com/";
        final String appID = "xxxxx";


        PAM.init(this, siteURL, appID );


    }
}
