package com.ashleyvenny.weatherassign;

import android.graphics.drawable.Drawable;

/**
 * Created by ashleyvo on 6/3/15.
 */
public class dayWeather {

   private int id;
   private String weatherStat;
   private String weatherDes;
   private String icon;
   private String icon_URL;
   private final String URL_FOR_ICON ="http://openweathermap.org/img/w/";
   private final String URL_FOR_ICON2=".png";
   private Drawable iconPic;

    public dayWeather() {
    }


    public int getId() {
        return id;
    }


    public void setIconPic(Drawable iconPic) {
        this.iconPic = iconPic;
    }

    public Drawable getIconPic() {
        return iconPic;
    }

    public String getIcon() {
        return icon;
    }

    public String getIcon_URL() {
        return icon_URL;
    }

    public void setIcon_url(String i) {
        icon_URL=URL_FOR_ICON+i+URL_FOR_ICON2;
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
