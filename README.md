# PamSdk-Android
Clien SDK to access PAM

## Requirements

 * Mimimum Android SDK version 15

## Installation

 1. Download pamservices.aar from lastes release

    ```
    https://github.com/pushandmotion/PAM-Client-android/releases/tag/1.0
    ```
    
 1. create new module in Android Studio
 ![create new module](https://raw.githubusercontent.com/pushandmotion/PAM-Client-android/master/readme_image/new_module.png)

 1. Choose import .Jar/.Arr Package
 
 ![import arr](https://raw.githubusercontent.com/pushandmotion/PAM-Client-android/master/readme_image/import.png)
 
 1. Then click Next button and browse to pamservices.aar file
 
 1. Open Module Setting for app project.
 
 1. Add pamservices to Module Dependency
 
 ![add pamservice to module dependency](https://raw.githubusercontent.com/pushandmotion/PAM-Client-android/master/readme_image/dependency.png)
 
## Usage

### Init PAM Client

 1. initialize PAM client in MainApplication.java
 
    ```java
     import com.pushandmotion.pamservices.*;
    ```
 
    ```java
     public class MainApplication extends Application{
         @Override
         public void onCreate() {
             super.onCreate();

             String PAM_APP_ID = "123456";
             String PAM_SERVER = "http://pam--------.com";

             PAM.initPam(this, PAM_SERVER, PAM_APP_ID );

         }
     }   
    ```
    
 1. Tracking your Activity pageview 
 
    MainActivity.java
    
    ```java
     import com.pushandmotion.pamservices.*;
    ```
   
   
    ```java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TrackingData data = PAM.createTackingDataBuilder()
                .setPageUrl("appscheme://myscheme/home")
                .build();
        
        String pageName = "home";
        PAM.trackPageView(pageName, data);
    }
    ```
  
 1. If you want to tracking the custom data field you can using the code below.
 
    ```java
     import java.util.Map;
     import com.pushandmotion.pamservices.*;
    ```
     
    ```java
     Map<String,String> customField = new HashMap<>();
     customField.put("email" , "customerEmail@gmail.com");
     customField.put("line_id" , "customerLineID");
     PAM.trackCustomField(customField);
    ```
  
 1. If you want to tracking Pam encrypting data ( also called as "UPDFH" ) you can using the code below.
 
    ```java
     import com.pushandmotion.pamservices.*;
    ```
     
    ```java
      PAM.trackUpdfh( updfhString );
    ```
 
