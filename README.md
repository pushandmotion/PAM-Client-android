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
 

## initialize PAM client in MainApplication.java
 
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
    
## Tracking your Activity pageview 
 
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

## Make Form Submit. 

### When you receive form submission from your app, you can forward those form data to PAM by calling submitForm method

```java
 import com.pushandmotion.pamservices.PAM;
 import com.pushandmotion.pamservices.data.Form;
```

```java
 String formId = "1234";
 Form form = new Form(formId);

 form.add("email" , "customerEmail@gmail.com");
 form.add("line_id" , "customerLineID");

 PAM.submitForm(form);
```
