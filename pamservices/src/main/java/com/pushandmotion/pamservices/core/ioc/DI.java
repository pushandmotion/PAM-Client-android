package com.pushandmotion.pamservices.core.ioc;

import com.pushandmotion.pamservices.core.http.BaseHTTPClient;
import com.pushandmotion.pamservices.core.http.PAMHTTPClient;

/**
 * Created by heart on 8/7/2017 AD.
 */

public class DI {
    private static BaseHTTPClient httpClient;

    public static BaseHTTPClient getHTTPClient(){
        if(httpClient == null){
            httpClient = new PAMHTTPClient();
        }
        return httpClient;
    }



}
