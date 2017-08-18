package pushandmotion.com.pam_android_test;

import android.app.Application;

import com.pushandmotion.pamservices.PAM;

/**
 * Created by mac on 8/16/2017 AD.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        PAM.init();

    }
}
