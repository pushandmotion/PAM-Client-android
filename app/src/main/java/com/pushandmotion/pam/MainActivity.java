package com.pushandmotion.pam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pushandmotion.pamservices.PAM;
import com.pushandmotion.pamservices.data.TrackingData;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TrackingData data = PAM.createTackingDataBuilder()
                .setPageUrl("tesco://app-id/home")
                .build();

        PAM.trackPageView("Home", data);

    }

}
