package com.pushandmotion.pamservices.core;

import com.pushandmotion.pamservices.core.http.BaseHTTPClient;
import com.pushandmotion.pamservices.core.ioc.DI;

/**
 * Created by heart on 8/7/2017 AD.
 */

public class PAMClient {

    public interface PAMClientListener{
        void onReady();
    }

    private String siteURL;
    private String eventServerURL;
    private PAMClientListener listener;
    private String apiKey;
    private BaseHTTPClient httpClient;

    private String token;
    private String eventServerURL;

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
        httpClient = DI.getHTTPClient();
        httpClient.post();

    }

}
