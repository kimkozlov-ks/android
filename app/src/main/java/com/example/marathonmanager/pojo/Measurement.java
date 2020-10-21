package com.example.marathonmanager.pojo;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Measurement {
    @SerializedName("weight")
    private double mWeight;
    @SerializedName("date")
    private Date mDate;

    public Measurement(){
    }

    public double getWeight() {

        return mWeight;
    }

    public void setWeight(double weight) {
        this.mWeight = weight;;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        this.mDate = date;
    }

    /*@Override
    public  String toString(){
        return String.valueOf(mWeight);
    }*/
}
