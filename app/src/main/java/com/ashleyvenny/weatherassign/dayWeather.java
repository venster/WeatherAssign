package com.ashleyvenny.weatherassign;

/**
 * Created by ashleyvo on 6/3/15.
 */
public class dayWeather {

   private int id;
   private String weatherStat;
   private String weatherDes;
   private String icon;

    public int getId() {
        return id;
    }

    public String getIcon() {
        return icon;
    }

    public String getWeatherDes() {
        return weatherDes;
    }

    public String getWeatherStat() {
        return weatherStat;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWeatherDes(String weatherDes) {
        this.weatherDes = weatherDes;
    }

    public void setWeatherStat(String weatherStat) {
        this.weatherStat = weatherStat;
    }
}
