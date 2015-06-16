package com.ashleyvenny.weatherassign;

import android.provider.BaseColumns;

/**
 * Created by ashleyvo on 6/14/15.
 */
public class Contract{
    public static final String DATABASE_NAME = "weather.db";

    public static final class PhotoEntry implements BaseColumns {

        public static final String TABLE_NAME = "day_entry";
        public static final String CITY = "city";
        public static final String DEGREE= "degree";
        public static final String WEATHER = "weather";
        public static final String WEATHER_DES = "weather_des";
        public static final String ICON_URL="icon_url";

    }

}
