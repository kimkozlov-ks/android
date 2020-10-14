package com.example.marathonmanager;

public class Measurement {
    private double weight;

    Measurement(){
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public  String toString(){
        return String.valueOf(weight);
    }
}
