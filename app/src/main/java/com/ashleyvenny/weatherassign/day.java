package com.ashleyvenny.weatherassign;

/**
 * Created by ashleyvo on 6/3/15.
 */
public class day {
    private tempOfDay tempDay;
    private double pressure;
    private double humid;
    private dayWeather weather;
    private double windspeed;
    private double windDirect;
    private double cloudPercent;


    public day(){

    }

    public void setCloudPercent(double cloudPercent) {
        this.cloudPercent = cloudPercent;
    }

    public void setHumid(double humid) {
        this.humid = humid;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }



    public void setTempDay(tempOfDay tempDay) {
        this.tempDay = tempDay;
    }

    public void setWeather(dayWeather weather) {
        this.weather = weather;
    }

    public void setWindDirect(double windDirect) {
        this.windDirect = windDirect;
    }

    public void setWindspeed(double windspeed) {
        this.windspeed = windspeed;
    }

    public dayWeather getWeather() {
        return weather;
    }

    public double getCloudPercent() {
        return cloudPercent;
    }

    public double getHumid() {
        return humid;
    }

    public double getPressure() {
        return pressure;
    }


    public double getWindDirect() {
        return windDirect;
    }

    public double getWindspeed() {
        return windspeed;
    }

    public tempOfDay getTempDay() {
        return tempDay;
    }
}
