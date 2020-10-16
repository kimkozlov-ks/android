package com.example.marathonmanager;

import com.google.gson.annotations.SerializedName;

public class AuthUser {
    @SerializedName("Phone")
    private String mPhone;
    @SerializedName("Password")
    private String mPassword;

    AuthUser(){
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        this.mPhone = phone;
    }

    public String getPassword() {
        return mPhone;
    }

    public void setPassword(String password) {
        this.mPassword = password;
    }

//    @Override
//    public  String toString(){
//        return  "{ \"Phone\": \"" + mPhone + "\"," +
//                "\"Password\": \"" + mPassword + "\"}";
//    }
}
