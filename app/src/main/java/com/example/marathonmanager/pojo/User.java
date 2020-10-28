package com.example.marathonmanager.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class User {

    @SerializedName("name")
    private String Name;

    @SerializedName("lastName")
    private String LastName;

    @SerializedName("birthday")
    private Date Birthday;

    @SerializedName("length")
    private double Length;

    @SerializedName("phone")
    private String Phone;


    public User(){
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public Date getBirthday() {
        return Birthday;
    }

    public void setBirthday(Date birthday) {
        Birthday = birthday;
    }

    public double getLength() {
        return Length;
    }

    public void setLength(double length) {
        Length = length;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
