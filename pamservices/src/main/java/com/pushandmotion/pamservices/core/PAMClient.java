package com.pushandmotion.pamservices.core;



import android.util.Log;

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
    private String apiKey;
    private String token;
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

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void start() {

        FormBody.Builder formBuilder = new FormBody.Builder()
            .add("apiKey", apiKey);

        String url = createSiteURL("init");

        Log.d(TAG,"URL="+url);

        Request request = new Request.Builder()
                .url( url )
                .post(formBuilder.build())
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onStartFail(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                JSONObject data = null;
                try {
                    data = new JSONObject(response.body().string());
                    token = data.getString("token");
                    eventServerURL =  data.getString("event_server");
                    listener.onStart();
                } catch (JSONException e) {
                    listener.onStartFail(e.getMessage());
                    e.printStackTrace();
                }

            }
        });

    }

    public void trackPageView(String pageName){

        FormBody.Builder formBuilder = new FormBody.Builder()
                .add("token", token)
                .add("apiKey", apiKey);

        String url = createEventURL("track");

        Request request = new Request.Builder()
                .url( url )
                .post( formBuilder.build() )
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onStartFail(e.getMessage());
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
        void onStartFail(String message);
    }

}
