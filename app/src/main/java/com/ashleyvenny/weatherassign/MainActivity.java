package com.ashleyvenny.weatherassign;

import android.content.Context;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.provider.SyncStateContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private TextView cityText;
    private TextView temp;
    private TextView weathertext;
    private TextView weatherDes;
    private coordinate coord;
    HttpURLConnection connection = null;
    WeatherData weather;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityText=(TextView) findViewById(R.id.textViewLoc);
        temp = (TextView) findViewById(R.id.textViewTemp);
        weathertext = (TextView) findViewById(R.id.textViewDescrip);
        weatherDes = (TextView) findViewById(R.id.textViewDescrip2);

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates (LocationManager.GPS_PROVIDER, 0, 0,locationListener);
        Log.i("TESTING", "HERE");
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        //Get GPS Information here
        public void onLocationChanged(Location location) {
            coord=new coordinate(location.getLongitude(),location.getLatitude());
            cityText.setText(Double.toString(coord.getLat()));
            temp.setText(Double.toString(location.getLatitude()));

            System.out.print("TESTING"+location.getLongitude());
            Log.i("TESTING", Double.toString(location.getLongitude()));

            String BASE_URL= "http://api.openweathermap.org/data/2.5/forecast/daily?lat=";
            String BASE_URL2="&lon=";
            String BASE_URL3="&cnt=10&mode=json&units=imperial";

//            String dataString = "https://api.flickr.com/services/rest/?method=flickr.photos.getRecent&api+key=" +
//                   Constants.API_KEY + "&per_page=" + Constants.NUM_PHOTOS + "&format=json&nojsoncallback=1";

            //TRY TO CALL API.  Does not work because Coordinate cannot find GPS
            try {
                URL dataUrl = new URL(BASE_URL+coord.getLat()+BASE_URL2+coord.getLong()+BASE_URL3);
                System.out.println(BASE_URL+coord.getLat()+BASE_URL2+coord.getLong()+BASE_URL3);
                connection = (HttpURLConnection) dataUrl.openConnection();
                connection.connect();
                int status = connection.getResponseCode();
                Log.d("TAG", "status " + status);
                //if it is successful
                if (status == 200) {
                    InputStream is = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    String responseString;
                    StringBuilder sb = new StringBuilder();

                    while ((responseString = reader.readLine()) != null) {
                        sb = sb.append(responseString);
                    }
                    String photoData = sb.toString();

                    // weather.parseInfo(photoData);


                    //return 0l;
                } else {
                    //return 1l;
                }
            } catch (MalformedURLException e) {

                e.printStackTrace();
                //return 1l;
            } catch (IOException e) {
                e.printStackTrace();
                //return 1l;
            }/* catch (JSONException e) {
            e.printStackTrace();
           // return 1l;
        } */finally {
                if (connection != null)
                    connection.disconnect();
            }

            cityText.setText(weather.getCityInfo().getName());

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {


        }

        @Override
        public void onProviderEnabled(String provider) {
            /*lat.setVisibility(View.VISIBLE);
            longa.setVisibility(View.VISIBLE);
            sign.setVisibility(View.INVISIBLE);
            notEnabled.setVisibility((View.INVISIBLE)); */



        }

        @Override
        public void onProviderDisabled(String provider) {
           /* lat.setVisibility(View.INVISIBLE);
            longa.setVisibility(View.INVISIBLE);
            sign.setVisibility(View.VISIBLE);
            notEnabled.setVisibility(View.VISIBLE);

            sign.setImageResource(R.drawable.no_gps);*/



        }
    };
/*    private class LoadPhotos extends AsyncTask<String, Long, Long> {
        HttpURLConnection connection = null;
        WeatherData weather;

        @Override
        protected void onPreExecute() {
            //progress.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Long... values) {
            super.onProgressUpdate(values);
        }

    protected Long doInBackground(String... strings) {
         String BASE_URL= "http://api.openweathermap.org/data/2.5/forecast/daily?lat=";
        String BASE_URL2="&lon=";
        String BASE_URL3="&cnt=10&mode=json&units=imperial";

//            String dataString = "https://api.flickr.com/services/rest/?method=flickr.photos.getRecent&api+key=" +
//                   Constants.API_KEY + "&per_page=" + Constants.NUM_PHOTOS + "&format=json&nojsoncallback=1";

        try {
            URL dataUrl = new URL(BASE_URL+coord.getLat()+BASE_URL2+coord.getLong()+BASE_URL3);
            connection = (HttpURLConnection) dataUrl.openConnection();
            connection.connect();
            int status = connection.getResponseCode();
            Log.d("TAG", "status " + status);
            //if it is successful
            if (status == 200) {
                InputStream is = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String responseString;
                StringBuilder sb = new StringBuilder();

                while ((responseString = reader.readLine()) != null) {
                    sb = sb.append(responseString);
                }
                String photoData = sb.toString();

                weather.parseInfo(photoData);


                return 0l;
            } else {
                return 1l;
            }
        } catch (MalformedURLException e) {

            e.printStackTrace();
            return 1l;
        } catch (IOException e) {
            e.printStackTrace();
            return 1l;
        } catch (JSONException e) {
            e.printStackTrace();
            return 1l;
        } finally {
            if (connection != null)
                connection.disconnect();
        }

    }*/
        /*@Override
        protected void onPostExecute(Long result) {
            if (result != 1l) {
                DataBaseHelper dbHelper = new DataBaseHelper(getApplicationContext());
                dbHelper.clearTable();
                dbHelper.addRows(photos);
                dbHelper.close();
                showList();

            } else {
                Toast.makeText(getApplicationContext(), "AsyncTask didn't complete", Toast.LENGTH_LONG).show();
            }
            progress.setVisibility(View.GONE);
        }*/
  //  }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
