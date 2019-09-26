# PLEASE NOTE, THIS SDK IS NO LONGER BEING MAINTAINED
## use the new version of SDK visit https://pushandmotion.com

# PamSdk-Android
Clien SDK to access PAM

## Requirements

 * Mimimum Android SDK version 15

## Install PAM client for android via Gradle.

 1. Add PAM maven repository to your project-level build.gradle file.

    ```
    repositories {
        ....
        maven {
            url 'https://android-repo.pushandmotion.com/artifactory/pam-maven/'
        }
        ....
    }
    ```
 1. Open your module-level build.gradle file and add PAM to dependencies
 
  ```
  dependencies {
    ...
    implementation 'com.pushandmotion:pamservices:1.1.1'
    ...
  }
  ```

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
    
    String pageTitle = "sample application title";
    PAM.trackPageView(pageTitle, data);
}
```

## Make a Form Submit. 

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
