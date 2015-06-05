package com.ashleyvenny.weatherassign;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * Created by ashleyvo on 6/3/15.
 */
public class WeatherHttpClient {

    private static String BASE_URL= "http://api.openweathermap.org/data/2.5/forecast/daily?lat=";
    private static String BASE_URL2="&lon=";
    private static String BASE_URL3="&cnt=10&mode=json&units=imperial";

    public String getOnlineData(coordinate c)
    {
        HttpURLConnection con=null;
        InputStream is=null;
        try{
            //request
            con = (HttpURLConnection) (new URL(BASE_URL + c.getLat()+BASE_URL2+c.getLong()+BASE_URL3).openConnection());
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

            //build the data
            StringBuffer buffer = new StringBuffer();
            is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while (  (line = br.readLine()) != null )
                buffer.append(line + "\r\n");

            is.close();
            con.disconnect();
            return buffer.toString();

        }
        catch(Throwable t) {
            t.printStackTrace();
        }
        finally {
            try { is.close(); } catch(Throwable t) {}
            try { con.disconnect(); } catch(Throwable t) {}
        }


        return null;
    }


}

