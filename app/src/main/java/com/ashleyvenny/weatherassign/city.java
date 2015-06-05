package com.ashleyvenny.weatherassign;

/**
 * Created by ashleyvo on 6/3/15.
 */
public class city {
    private String name;
    private coordinate coord;
    private String country;
    private int popul;

    public city()
    {
        name="";
        coord=new coordinate(0,0);
        country="";
        popul=0;
    }

    public city(String n,coordinate c,String count,int pop)
    {
        name=n;
        coord=c;
        country=count;
        popul=pop;
    }
    public city(String n,double longi, double lat,String count,int pop)
    {
        name=n;
        coord=new coordinate(longi,lat);
        country=count;
        popul=pop;
    }
    public void setCoord(coordinate coord) {
        this.coord = coord;
    }
    public void setCoord(double lon,double lat){
        coordinate c=new coordinate(lon,lat);
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPopul(int popul) {
        this.popul = popul;
    }
    public String getName()
    {
        return name;
    }
    public coordinate getCoord()
    {
        return coord;
    }
    public String getCountry()
    {
        return country;
    }

    public int getPopul() {
        return popul;
    }
};
