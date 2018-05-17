package com.pushandmotion.pam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pushandmotion.pamservices.PAM;
import com.pushandmotion.pamservices.data.Form;
import com.pushandmotion.pamservices.data.TrackingData;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    Button trackBTN;
    Button updfhBTN;
    EditText textPageName;
    EditText textUPDFH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        trackBTN = (Button)findViewById(R.id.trackBTN);
        updfhBTN = (Button)findViewById(R.id.updfhBTN);
        textPageName = (EditText)findViewById(R.id.textPageName);
        textUPDFH = (EditText)findViewById(R.id.textUPDFH);
        trackBTN.setOnClickListener(this);
        updfhBTN.setOnClickListener(this);


        //---- Track Pageview
        TrackingData data = PAM.createTackingDataBuilder()
                .setPageUrl("tesco://app-id/home")
                .build();
        PAM.trackPageView("Home", data);
        //------- Track Pageview

        //===================================

        //---- Submit Form ----
        String formId = "1234";
        Form form = new Form(formId);

        form.add("email" , "customerEmail@gmail.com");
        form.add("line_id" , "customerLineID");

        PAM.submitForm(form);
        //---- Submit Form ----

    }

    @Override
    public void onClick(View v) {
        if(v == trackBTN){
            PAM.trackPageView(textPageName.getText().toString());
        }else if(v == updfhBTN){
            PAM.trackUpdfh(textUPDFH.getText().toString());
        }
    }

}
