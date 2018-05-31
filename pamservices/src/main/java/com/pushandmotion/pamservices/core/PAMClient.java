package com.pushandmotion.pamservices.core;



import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.pushandmotion.pamservices.PAM;
import com.pushandmotion.pamservices.data.PAMLocalDataBase;
import com.pushandmotion.pamservices.data.TrackingData;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

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

    private PAMClientListener listener;
    private String updfh;
    private String pamServerURL;
    private OkHttpClient httpClient;

    private String TAG = "PAM Debug";

    public PAMClient(){
        httpClient = new OkHttpClient();
    }

    public void setPAMUrl(String pamServerURL) {
        this.pamServerURL = pamServerURL;
    }

    public void setListener(PAMClientListener listener) {
        this.listener = listener;
    }

    public void trackPageView(TrackingData data){
        trackPageView(data,null);
    }

    public void trackPageView(TrackingData data, Map<String,String> cuttomFields){

        FormBody.Builder formBuilder = new FormBody.Builder()
                .add("app_id", data.appId )
                .add("page_language" , data.page_language )
                .add("resolution",data.resolution)
                .add("timezone_offset",data.timezone_offset.toString())
                .add("platform", data.platform)
                .add("do_not_track",data.do_not_track)
                .add("adblock",data.adblock);

        if( cuttomFields != null ){
            Iterator it = cuttomFields.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                formBuilder.add(pair.getKey().toString(),  pair.getValue().toString() );
                it.remove();
            }
        }

        if(data.updfh != null) {
            formBuilder.add("updfh", data.updfh);
        }

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
            Log.d("PAM","POST mtc_id = "+data.mtc_id );
        }else{
            Log.d("PAM","POST mtc_id = NULL");
        }

        if(data.sid != null){
            formBuilder.add("sid", data.sid );
        }

        formBuilder.add("fingerprint", data.getFingerPrint() );


        String url = createEventURL("rest/event");

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


                final String result = response.body().string();

                Handler mainHandler = new Handler(Looper.getMainLooper());
                Runnable myRunnable = new Runnable() {
                    @Override
                    public void run() {
                        onTrackingSuccess(result);
                    }
                };
                mainHandler.post(myRunnable);

            }
        });
    }

    private void onTrackingSuccess(String result){

        Log.d(TAG,result );

        try {
            JSONObject body = new JSONObject(result);
            String mtc_id = body.getString("id");
            String sid = body.getString("sid");

            PAM.defaultTrackingData().setMtcId(mtc_id);
            PAM.defaultTrackingData().setSid(sid);

            PAMLocalDataBase.getInstance(PAM.getContext()).saveMtcID(mtc_id,sid);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String createEventURL(String url) {
        return pamServerURL+url;
    }

    public interface PAMClientListener{
        //void onUpdateData(JSONObject data);
    }

}
