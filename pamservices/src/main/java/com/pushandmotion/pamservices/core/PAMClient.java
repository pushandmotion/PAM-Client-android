package com.pushandmotion.pamservices.core;



import android.util.Log;

import com.pushandmotion.pamservices.PAM;
import com.pushandmotion.pamservices.data.TrackingData;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by heart on 8/7/2017 AD.
 */

public class PAMClient {

    private String siteURL;
    private PAMClientListener listener;
    private String updfh;
    private String eventServerURL;
    private OkHttpClient httpClient;

    private String TAG = "PAM Debug";

    private String tid;


    public PAMClient(){
        httpClient = new OkHttpClient();
    }

    public void setSiteURL(String siteURL) {
        this.siteURL = siteURL;
    }

    public void setEventServerURL(String eventServerURL) {
        this.eventServerURL = eventServerURL;
    }

    public void setListener(PAMClientListener listener) {
        this.listener = listener;
    }


    public void start(String appId) {

        FormBody.Builder formBuilder = new FormBody.Builder()
            .add("apiKey", appId);

        String url = createSiteURL("init");

        Request request = new Request.Builder()
                .url( url )
                .post(formBuilder.build())
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //listener.onStartFail(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                JSONObject data = null;
                try {
                    data = new JSONObject(response.body().string());
                    updfh = data.getString("token");
                    eventServerURL =  data.getString("event_server");
                    setEventServerURL(eventServerURL);

                    PAM.defaultTrackingData().setUpdfh(updfh);

                    listener.onStart();
                } catch (JSONException e) {
                    //listener.onStartFail(e.getMessage());
                    e.printStackTrace();
                }

            }
        });

    }

    public void trackPageView(TrackingData data){

        FormBody.Builder formBuilder = new FormBody.Builder()
                .add("updfh", data.updfh)
                .add("app_id", data.appId )
                .add("page_language" , data.page_language )
                .add("resolution",data.resolution)
                .add("timezone_offset",data.timezone_offset.toString())
                .add("platform", data.platform)
                .add("do_not_track",data.do_not_track)
                .add("adblock",data.adblock);


        if(data.page_title != null){
            formBuilder.add("page_title", data.page_title );
        }

        if(data.page_referrer != null){
            formBuilder.add("page_referrer", data.page_referrer );
        }

        if(data.page_url != null){
            formBuilder.add("page_url", data.page_url );
        }

        if(data.counter != null){
            formBuilder.add("counter", data.counter.toString() );
        }

        if(data.mtc_id != null){
            formBuilder.add("mtc_id", data.mtc_id );
        }


        formBuilder.add("fingerprint", data.getFingerPrint() );

        String url = createEventURL("track");

        Request request = new Request.Builder()
                .url( url )
                .post( formBuilder.build() )
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Headers responseHeaders = response.headers();
                for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                    Log.d(TAG, responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }
                Log.d(TAG,response.body().string());

                try {
                    JSONObject body = new JSONObject(response.body().string());
                    tid = body.getString("tid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private String createEventURL(String url) {
        return eventServerURL+url;
    }
    private String createSiteURL(String url) {
        return siteURL+url;
    }

    public interface PAMClientListener{
        void onStart();
    }

}
