package com.ashleyvenny.weatherassign;

/**
 * Created by ashleyvo on 6/3/15.
 */
public class coordinate {
    double longi;
    double lat;

    public coordinate ()
    {
        longi=0;
        lat=0;
    }
    public coordinate(double longi, double lat){
        this.longi=longi;
        this.lat=lat;
    }
    public void setLong(double l){
        longi=l;
    }
    public void setLat(double l)
    {
        lat=l;
    }
    public  double getLong()
    {
        return longi;
    }
    public double getLat()
    {
        return lat;
    }

}
