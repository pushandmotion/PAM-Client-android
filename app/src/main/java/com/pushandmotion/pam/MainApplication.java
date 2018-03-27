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

        final String appID = getResources().getString(R.string.pam_app_id);
        final String PAM_URL = getResources().getString(R.string.pam_url);

        PAM.initPam(this, PAM_URL, appID );

    }
}
