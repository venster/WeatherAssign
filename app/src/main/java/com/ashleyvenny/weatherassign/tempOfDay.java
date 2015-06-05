package com.ashleyvenny.weatherassign;

/**
 * Created by ashleyvo on 6/3/15.
 */
public class tempOfDay {
    private double day;
    private double min;
    private double max;
    private double night;
    private double eve;
    private double morn;

    public tempOfDay()
    {

    }
    public tempOfDay(double d, double min, double max, double night, double eve, double morn)
    {
        day=d;
        this.min=min;
        this.max=max;
        this.night=night;
        this.eve=eve;
        this.morn=morn;
    }

    public double getDay() {
        return day;
    }

    public double getEve() {
        return eve;
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }

    public double getMorn() {
        return morn;
    }

    public double getNight() {
        return night;
    }

    public void setDay(double day) {
        this.day = day;
    }

    public void setEve(double eve) {
        this.eve = eve;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public void setMorn(double morn) {
        this.morn = morn;
    }

    public void setNight(double night) {
        this.night = night;
    }

}
