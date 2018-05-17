package com.pushandmotion.pamservices.data;

import java.util.HashMap;
import java.util.Map;

public class Form {

    private Map<String,String> formData;
    private String formId;

    public Form(String formId){
        this.formId = formId;
        formData = new HashMap<>();
        formData.put("formId", formId);
    }

    public void add(String name,String value){
        formData.put(name,value);
    }

    public Map<String,String> getFormDataAsMap(){
        return formData;
    }

    public String getFormId() {
        return formId;
    }

}
