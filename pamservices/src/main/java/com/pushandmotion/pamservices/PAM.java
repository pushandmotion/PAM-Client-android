package com.pushandmotion.pamservices;

import android.content.Context;

import com.pushandmotion.pamservices.core.PAMClient;
import com.pushandmotion.pamservices.data.PAMLocalDataBase;
import com.pushandmotion.pamservices.data.TrackingData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by heart on 8/7/2017 AD.
 */

public class PAM {
    private static PAMClient pam;
    private static Context context;
    private static TrackingData.Builder defaultTrackingDataBuilder;

    public static TrackingData.Builder defaultTrackingData(){
        if(defaultTrackingDataBuilder == null){
            defaultTrackingDataBuilder = new TrackingData.Builder();
        }
        return defaultTrackingDataBuilder;
    }

    public static void initPam(Context c, String pamURL, String appID){

        defaultTrackingData()
                .setAppId(appID)
                .setCounter(0);

        String updfh = PAMLocalDataBase.getInstance(c).getUPDFH();
        String mtc_id = PAMLocalDataBase.getInstance(c).getMtcID();
        String sid = PAMLocalDataBase.getInstance(c).getSID();

        if(updfh != null){
            defaultTrackingData().setUpdfh(updfh);
        }

        if(mtc_id != null){
            defaultTrackingData().setMtcId(mtc_id);
        }

        if(sid != null){
            defaultTrackingData().setSid(sid);
        }

        context = c.getApplicationContext();
        pam = new PAMClient();
        pam.setPAMUrl(pamURL);

        pam.setListener(new PAMClient.PAMClientListener(){

        });

    }



    public static Context getContext(){
        return context;
    }

    public static void trackPageView(TrackingData data){
        pam.trackPageView(data);
    }
    public static void trackUpdfh(String updfh){
        PAMLocalDataBase.getInstance(context).saveUPDFH(updfh);
        defaultTrackingData().setUpdfh(updfh);
        trackPageView("updfh");
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

    public static void trackCustomField(Map<String,String> cuttomFields){
        TrackingData data = defaultTrackingData().clone().setPageTitle("custom field").build();
        pam.trackPageView(data, cuttomFields);
    }

}
