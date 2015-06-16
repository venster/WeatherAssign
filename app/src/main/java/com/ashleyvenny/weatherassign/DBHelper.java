package com.ashleyvenny.weatherassign;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ashleyvo on 6/14/15.
 */
public class DBHelper extends SQLiteOpenHelper{

    ArrayList<day> arrayData = new ArrayList<day>();



        private String[] projection = {
                Contract.PhotoEntry._ID,
                Contract.PhotoEntry.CITY,
                Contract.PhotoEntry.DEGREE,
                Contract.PhotoEntry.WEATHER,
                Contract.PhotoEntry.WEATHER_DES,
                Contract.PhotoEntry.ICON_URL,};


        private static final String DATABASE_CREATE =
                "CREATE TABLE " +
                        Contract.PhotoEntry.TABLE_NAME + " (" +
                        Contract.PhotoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        Contract.PhotoEntry.CITY + " TEXT NOT NULL, " +
                        Contract.PhotoEntry.DEGREE + " DOUBLE NOT NULL, " +
                        Contract.PhotoEntry.WEATHER + " TEXT NOT NULL, " +
                        Contract.PhotoEntry.WEATHER_DES + " TEXT NOT NULL, " +
                        Contract.PhotoEntry.ICON_URL + " TEXT NOT NULL " +")";


        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + Contract.PhotoEntry.TABLE_NAME;
    private static final String SQL_SELECT_ALL = "SELECT * FROM "+Contract.PhotoEntry.TABLE_NAME;

        public DBHelper(Context context) {
            super(context, Contract.DATABASE_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i("TEST", "Create table command: " + DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_ENTRIES);
        }

        public void insertPhotoEntry(WeatherData data,int i) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues cv = new ContentValues();


            cv.put(Contract.PhotoEntry.CITY, data.getCityInfo().getName());
            cv.put(Contract.PhotoEntry.DEGREE, data.getDay(i).getTempDay().getDay());
            cv.put(Contract.PhotoEntry.WEATHER, data.getDay(i).getWeather().getWeatherStat());
            cv.put(Contract.PhotoEntry.WEATHER_DES, data.getDay(i).getWeather().getWeatherDes());
            cv.put(Contract.PhotoEntry.ICON_URL, data.getDay(i).getWeather().getIcon_URL());


            db.insert(Contract.PhotoEntry.TABLE_NAME, null, cv);
        }

        public Cursor getAllRows() {
            SQLiteDatabase db = getReadableDatabase();
            return db.query(Contract.PhotoEntry.TABLE_NAME, projection, null, null, null, null, null);

//        Here's the method with arguments:
//        public Cursor query (String table, String[] columns, String selection, String[]
//        selectionArgs, String groupBy, String orderBy, String limit)

        }

        public Cursor getRowByID(long id) {
            SQLiteDatabase db = getReadableDatabase();
            String[] ids = {String.valueOf(id)};
            return db.query(Contract.PhotoEntry.TABLE_NAME, projection, Contract.PhotoEntry._ID + "==?", ids, null, null, null);
        }

        public void deleteRow(long id) {
            SQLiteDatabase db = getWritableDatabase();
            String[] ids = {String.valueOf(id)};
            db.delete(Contract.PhotoEntry.TABLE_NAME, Contract.PhotoEntry._ID + "==?", ids);
        }

        public void addRows(WeatherData data) {
            for (int x=0;x<data.getTenDay().size();x++) {
                insertPhotoEntry(data,x);
            }
        }

        public void clearTable() {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("delete from " + Contract.PhotoEntry.TABLE_NAME);
        }

        public void dropTable() {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(SQL_DELETE_ENTRIES);
        }
        public void printTable(){

            SQLiteDatabase db = getWritableDatabase();
            Cursor  cursor = db.rawQuery(SQL_SELECT_ALL,null);
            if (cursor .moveToFirst()) {

                while (cursor.isAfterLast() == false) {
                    String name = cursor.getString(cursor
                            .getColumnIndex(Contract.PhotoEntry.ICON_URL));

                    Log.d("WEATHER ENTRY",name);
                    cursor.moveToNext();
                }
            }


        }



    public List<day> retrieveData() {

        SQLiteDatabase db = getWritableDatabase();
        day daydata=new day();
        dayWeather weatherEntry=new dayWeather();
        tempOfDay tempEntry = new tempOfDay();
        Cursor cursor = db.rawQuery(SQL_SELECT_ALL, null);
        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                weatherEntry.setIcon_url(cursor.getString(cursor.getColumnIndex(Contract.PhotoEntry.ICON_URL)));
                weatherEntry.setWeatherDes(cursor.getString(cursor.getColumnIndex(Contract.PhotoEntry.WEATHER_DES)));
                weatherEntry.setWeatherStat(cursor.getString(cursor.getColumnIndex(Contract.PhotoEntry.WEATHER)));
                tempEntry.setDay(cursor.getDouble(cursor.getColumnIndex(Contract.PhotoEntry.DEGREE)));

                daydata.setWeather(weatherEntry);
                daydata.setTempDay(tempEntry);
                arrayData.add(daydata);
                cursor.moveToNext();
            }
        }

        return arrayData;
    }
}



