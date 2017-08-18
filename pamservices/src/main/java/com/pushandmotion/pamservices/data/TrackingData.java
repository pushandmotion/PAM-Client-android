package com.pushandmotion.pamservices.data;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import com.pushandmotion.pamservices.PAM;
import com.pushandmotion.pamservices.core.FingerPrint;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by mac on 8/18/2017 AD.
 */

public class TrackingData {

    public String page_title= null;
    public String page_language= null;
    public String page_referrer= null;
    public String page_url= null;
    public String appId= null;
    public String resolution= null;
    public Integer timezone_offset = null;
    public Integer counter = null;
    public String platform = "android";
    public String do_not_track = "unspecified";
    public String adblock = "false";
    public String updfh = null;
    public String mtc_id = null;

    public String getFingerPrint(){

        String str = updfh
                +appId
                +page_language
                +resolution
                +timezone_offset.toString()
                +platform
                +do_not_track
                +adblock;

        if(page_title != null){
            str += page_title;
        }

        if(page_referrer != null){
            str += page_referrer;
        }

        if(page_url != null){
            str += page_url;
        }

        if(counter != null){
            str += counter.toString();
        }

        return FingerPrint.fromString(str);
    }


    public static class Builder{
        private String page_title = null;
        private String page_language = null;
        private String page_referrer = null;
        private String page_url = null;
        private Integer counter = null;
        private String resolution = null;
        private String updfh = null;
        private String appId = null;
        private String mtc_id = null;

        public TrackingData build(){
            TrackingData data = new TrackingData();
            data.page_title = page_title;

            data.page_referrer = page_referrer;
            data.page_url = page_url;
            data.counter = counter;
            data.appId = appId;

            if(resolution == null){
                setResolutionFromDeviceScreen();
            }
            data.resolution = resolution;
            if(page_language == null){
                setPageLanguageFromDeviceConfiguarion();
            }
            data.page_language = page_language;

            data.timezone_offset = getTimezoneOffset();

            return data;
        }

        public Builder clone(){
            return new Builder()
                    .setAppId(appId)
                    .setMtcId(mtc_id)
                    .setUpdfh(updfh)
                    .setPageTitle(page_title)
                    .setPageLanguage(page_language)
                    .setPageReferrer(page_referrer)
                    .setPageUrl(page_url)
                    .setCounter(counter)
                    .setResolution(resolution);
        }



        public static int getTimezoneOffset() {

            TimeZone tz = TimeZone.getDefault();
            Calendar cal = GregorianCalendar.getInstance(tz);
            int offsetInMillis = tz.getOffset(cal.getTimeInMillis());

            double offsetHour = Math.abs(offsetInMillis / 3600000);
            double offsetMinute = Math.abs((offsetInMillis / 60000) % 60) + (offsetHour*60);
            int offset = (int)  offsetMinute*-1;
            return offset;
        }

        public Builder setMtcId(String mtc_id) {
            this.mtc_id = mtc_id;
            return this;
        }

        public Builder setUpdfh(String updfh) {
            this.updfh = updfh;
            return this;
        }

        public Builder setAppId(String appId) {
            this.appId = appId;
            return this;
        }

        public Builder setPageTitle(String page_title) {
            this.page_title = page_title;
            return  this;
        }

        public Builder setPageLanguage(String page_language) {
            this.page_language = page_language;
            return  this;
        }

        public Builder setPageLanguageFromDeviceConfiguarion() {
            this.page_language = Locale.getDefault().getDisplayLanguage();;
            return  this;
        }

        public Builder setPageReferrer(String page_referrer) {
            this.page_referrer = page_referrer;
            return  this;
        }

        public Builder setPageUrl(String page_url) {
            this.page_url = page_url;
            return  this;
        }

        public Builder setCounter(int counter) {
            this.counter = counter;
            return  this;
        }

        public Builder setResolution(int width,int height) {
            this.resolution = width+"x"+height;
            return  this;
        }

        public Builder setResolution(String resulotion) {
            this.resolution = resulotion;
            return  this;
        }

        public Builder setResolutionFromDeviceScreen() {
            WindowManager windowManager = (WindowManager) PAM.getContext()
                    .getSystemService(Context.WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;
            this.resolution = width+"x"+height;
            return  this;
        }

    }

}
