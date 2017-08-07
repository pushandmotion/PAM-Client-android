package com.pushandmotion.pamservices.core.http;

import okhttp3.OkHttpClient;

/**
 * Created by heart on 8/7/2017 AD.
 */

public class PAMHTTPClient extends BaseHTTPClient {
    private OkHttpClient client;

    public PAMHTTPClient(){
        client = new OkHttpClient();
    }

    @Override
    public void post(){
        
    }


}
