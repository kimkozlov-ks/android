package com.example.marathonmanager.pojo;

import com.google.gson.annotations.SerializedName;

public class AccessToken {
    @SerializedName("accessToken")
    private String mAccessToken;

    AccessToken(){
    }

    public String getAccessToken() {
        return mAccessToken;
    }

    public void setAccessToken(String accessToken) {
        this.mAccessToken = accessToken;
    }
}
