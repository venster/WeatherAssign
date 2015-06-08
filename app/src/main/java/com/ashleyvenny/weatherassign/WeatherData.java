package com.ashleyvenny.weatherassign;

import android.location.Location;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ashleyvo on 5/30/15.
 */
public class WeatherData {


    private city cityInfo;
    private ArrayList<day> tenDay;

public WeatherData(){
             cityInfo=new city();
            tenDay=new ArrayList<day>();
    }



    public  void parseInfo(String data) throws JSONException {
        //separate the data in to the objects and array

        JSONObject JObj = new JSONObject(data);
        //Log.d("Parsing",data);

        JSONObject Jcityinfo = getObject("city",JObj);
        JSONObject JcoordInfo = getObject("coord",Jcityinfo);
        JSONArray tenday = JObj.getJSONArray("list");
        cityInfo=new city();


        try {
            //the city info part of the Data
            cityInfo.setCoord(getDouble("lon",JcoordInfo),getDouble("lat",JcoordInfo));
            Log.d("Long and Lat",Double.toString(cityInfo.getCoord().getLat()) + " " +Double.toString(cityInfo.getCoord().getLat()));
            cityInfo.setName(getString("name", Jcityinfo));
            cityInfo.setCountry(getString("country",Jcityinfo));
            cityInfo.setPopul(getInt("population",Jcityinfo));

            int arraysize = getInt("cnt",JObj);
            createWeatherArray(tenday,arraysize);


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    public city getCityInfo() {
        return cityInfo;
    }

    public day getDay(int x) {
        return tenDay.get(x);
    }

    public ArrayList<day> getTenDay() {
        return tenDay;
    }

    private static JSONObject getObject(String tagName, JSONObject jObj)  throws JSONException {
        JSONObject subObj = jObj.getJSONObject(tagName);
        return subObj;
    }

    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }

    private static Double  getDouble(String tagName, JSONObject jObj) throws JSONException {
        return (Double) jObj.getDouble(tagName);
    }

    private static int  getInt(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getInt(tagName);
    }
    private void createWeatherArray(JSONArray ten, int cnt) throws JSONException {


        tenDay = new ArrayList<day>();
        for(int x=0;x<cnt;x++) {

            //get the object of one day
            JSONObject JSONWeather = ten.getJSONObject(x);
            //get the temp info of oneday
            JSONObject JSONtemp = JSONWeather.getJSONObject("temp");

            //object in array
            day oneday = new day();

            //set the temp object to put in oneday
            tempOfDay tempDay = new tempOfDay();
            tempDay.setDay(getDouble("day", JSONtemp));
            tempDay.setMin(getDouble("min", JSONtemp));
            tempDay.setMax(getDouble("max",JSONtemp));
            tempDay.setNight(getDouble("night",JSONtemp));
            tempDay.setEve(getDouble("eve",JSONtemp));
            tempDay.setMorn(getDouble("morn",JSONtemp));
            //set the Tempday
            oneday.setTempDay(tempDay);

            oneday.setPressure(getDouble("pressure",JSONWeather));
            oneday.setHumid(getDouble("humidity",JSONWeather));

            //create weather object to get info for the weather
            JSONArray JSONWeatherinfoArray = JSONWeather.getJSONArray("weather");
            JSONObject JSONWeatherInfo = JSONWeatherinfoArray.getJSONObject(0);

            dayWeather dayweather=new dayWeather();

            dayweather.setId(getInt("id",JSONWeatherInfo));
            dayweather.setIcon(getString("icon",JSONWeatherInfo));
            dayweather.setWeatherStat(getString("main",JSONWeatherInfo));
            dayweather.setWeatherDes(getString("description",JSONWeatherInfo));
            //set the weather
            oneday.setWeather(dayweather);

            oneday.setWindspeed(getDouble("speed",JSONWeather));
            oneday.setWindDirect(getDouble("deg",JSONWeather));
            oneday.setCloudPercent(getDouble("clouds",JSONWeather));

            Log.d("MAKING LIST", oneday.getWeather().getWeatherDes());

            tenDay.add(oneday);
        }

    }
}



