package com.ashleyvenny.weatherassign;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.provider.SyncStateContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    TextView cityText;
    private TextView temp;
    private TextView weathertext;
    private TextView weatherDes;
    private coordinate coord=new coordinate(0,0);
    private Button button;

    HttpURLConnection connection = null;
    List<day> tendays;
    private FrameLayout iconWeather;
    URL picURL;


    WeatherData weather= new WeatherData(); //all of the weather data with city info

    private ProgressBar progressLoading;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityText=(TextView) findViewById(R.id.textViewLoc);
        temp = (TextView) findViewById(R.id.textViewTemp);
        weathertext = (TextView) findViewById(R.id.textViewDescrip);
        weatherDes = (TextView) findViewById(R.id.textViewDescrip2);
        button= (Button) findViewById(R.id.button);
        progressLoading = (ProgressBar) findViewById(R.id.progressBar);

        iconWeather= (FrameLayout) findViewById(R.id.wIcon);


        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 50000, 10,locationListener);




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLoadTask(MainActivity.this);
               /* List<day> tendays=weather.getTenDay();
                ArrayAdapter<day> tendayArrayAdapter =
                        new ArrayAdapter<day>(MainActivity.this,android.R.layout.simple_list_item_1,tendays);
                ListView listview = (ListView) findViewById(android.R.id.list);
                listview.setAdapter(tendayArrayAdapter);*/

            }
        });






    }

    public void startLoadTask(Context c){
        if (isOnline()) {
            LoadData task = new LoadData();
            task.execute();
        } else {
            Toast.makeText(c, "Not online", Toast.LENGTH_LONG).show();
        }
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
    LocationListener locationListener = new LocationListener() {
        @Override
        //Get GPS Information here
        public void onLocationChanged(Location location) {
            coord.setLat(location.getLatitude());
            coord.setLong(location.getLongitude());
            //weathertext.setText(Double.toString(coord.getLat()));
            //temp.setText(Double.toString(location.getLatitude()));
            startLoadTask(MainActivity.this);





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
    private class LoadData extends AsyncTask<String, Long, Long> {
        HttpURLConnection connection = null;


        @Override
        protected void onPreExecute() {
            progressLoading.setVisibility(View.VISIBLE);
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
            Log.d("URL",BASE_URL+coord.getLat()+BASE_URL2+coord.getLong()+BASE_URL3);
            connection = (HttpURLConnection) dataUrl.openConnection();
            connection.connect();
            int status = connection.getResponseCode();
            //Log.d("TAG", "status " + status);
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

               // Log.d("TAG", photoData);
                weather.parseInfo(photoData);

                Log.d("AFTER PARSE", weather.getDay(1).getWeather().getWeatherStat());
                //make iconlist
                for(int x=0;x<weather.getTenDay().size();x++) {
                  picURL = new URL(weather.getDay(x).getWeather().getIcon_URL());
                  InputStream content = (InputStream) picURL.getContent();
                  weather.getDay(x).getWeather().setIconPic(Drawable.createFromStream(content, "src"));
                }


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
            Log.d("TAG", "failparse");
            e.printStackTrace();
            return 1l;
        } finally {
            if (connection != null)
                connection.disconnect();
        }

    }
        @Override
        protected void onPostExecute(Long result) {
            if (result != 1l) {
                cityText.setText(weather.getCityInfo().getName());
                weathertext.setText(weather.getDay(0).getWeather().getWeatherStat());
                weatherDes.setText(weather.getDay(0).getWeather().getWeatherDes());
                temp.setText(Double.toString(weather.getDay(0).getTempDay().getDay()));
                iconWeather.setBackground(weather.getDay(0).getWeather().getIconPic());

                DBHelper dbHelper = new DBHelper(getApplicationContext());
                dbHelper.clearTable();
                dbHelper.addRows(weather);
                dbHelper.printTable();
                dbHelper.close();

                List<day> tendays=weather.getTenDay();
                ArrayAdapter<day> tendayArrayAdapter =
                        new weatherArrayAdapter(MainActivity.this,0,tendays);
                ListView listview = (ListView) findViewById(android.R.id.list);
                listview.setAdapter(tendayArrayAdapter);



            } else {
                Toast.makeText(getApplicationContext(), "AsyncTask didn't complete", Toast.LENGTH_LONG).show();
            }
            progressLoading.setVisibility(View.GONE);
        }
    }


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

    class weatherArrayAdapter extends ArrayAdapter<day>{

        Context context;
        List<day> objects;

        public weatherArrayAdapter(Context context, int resource, List<day> objects) {
            super(context, resource, objects);
            this.context=context;
            this.objects=objects;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            day Day = objects.get(position);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            View view=inflater.inflate(R.layout.weather_item,null);

            TextView Deg = (TextView) view.findViewById(R.id.tempNum);
            TextView weatherTxt = (TextView) view.findViewById(R.id.weatherInfoText);
            TextView weatherDesTxt =(TextView) view.findViewById(R.id.weatherDesTxt);
            FrameLayout iconW = (FrameLayout) view.findViewById(R.id.wIcon2);

            Deg.setText(Double.toString(Day.getTempDay().getDay()));
            weatherTxt.setText(Day.getWeather().getWeatherStat());
            weatherDesTxt.setText(Day.getWeather().getWeatherDes());
            iconW.setBackground(Day.getWeather().getIconPic());

           /* DBHelper dbHelper = new DBHelper(MainActivity.this);
            Cursor cursor = dbHelper.getAllRows();
            TextView Deg = (TextView) view.findViewById(R.id.tempNum); */



            return view;
        }
    }

}
