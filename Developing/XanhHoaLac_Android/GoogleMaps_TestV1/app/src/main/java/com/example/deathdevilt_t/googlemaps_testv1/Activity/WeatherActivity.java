package com.example.deathdevilt_t.googlemaps_testv1.Activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.WeatherApi.model.List1;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.WeatherApi.model.Main;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.WeatherApi.model.SOAnswersResponse;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.WeatherApi.model.Service.SOService;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.WeatherApi.model.Service.ServiceFactory;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.WeatherApi.model.Weather;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.WeatherApi.model.Wind;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.WeatherApi.model.recycleView.WeatherAdapter;
import com.example.deathdevilt_t.googlemaps_testv1.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by DeathDevil.T_T on 26-Jun-17.
 */

public class WeatherActivity extends AppCompatActivity {
    private SOService soService;
    private SOAnswersResponse soAnswersResponse;

    private List<Main> mainList = new ArrayList<>();
    private List<Wind> windList = new ArrayList<>();
    private List<Weather> weatherList = new ArrayList<>();
    private List<List1> time_daily = new ArrayList<>();
    private RecyclerView recyclerView;
    private WeatherAdapter mAdapter;

    private TextView txt_temp, txt_humidity, txt_windy;
    private ImageView img_weather;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navi_weatherapi);

        init();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void init() {
        txt_temp = (TextView) findViewById(R.id.txt_temp);
        txt_humidity = (TextView) findViewById(R.id.txt_humidity);
        txt_windy = (TextView) findViewById(R.id.txt_windy);
        img_weather = (ImageView) findViewById(R.id.img_Icon);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        mAdapter = new WeatherAdapter(mainList, weatherList, time_daily, this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        soService = ServiceFactory.getInst().createService(SOService.class);
        Call<SOAnswersResponse> call = soService.getAnswers();
        call.enqueue(new Callback<SOAnswersResponse>() {
            @Override
            public void onResponse(Call<SOAnswersResponse> call, Response<SOAnswersResponse> response) {
                if (response.isSuccessful()) {
                    soAnswersResponse = response.body();
                    for (List1 list1 : soAnswersResponse.getList1List()) {
                        DecimalFormat df = new DecimalFormat("#.0", new DecimalFormatSymbols(Locale.ENGLISH));
                        Main main = new Main(Double.valueOf(df.format(list1.getMain().getTempMax() - 273.15)), list1.getMain().getHumidity());
                        List1 list11 = new List1(list1.getDtTxt());
                        Wind wind = new Wind(list1.getWind().getSpeed());
                        time_daily.add(list11);
                        mainList.add(main);
                        windList.add(wind);

                        for (Weather weather : list1.getWeather()) {
                            Weather weather1 = new Weather(weather.getIcon());
                            weatherList.add(weather1);
                        }

                    }
                    mAdapter.notifyDataSetChanged();
                    txt_temp.setText(mainList.get(2).getTempMax() + "°C");
                    txt_humidity.setText(mainList.get(2).getHumidity() + "");
                    txt_windy.setText(windList.get(2).getSpeed() + "");

                    if(weatherList.get(3).getIcon().equals("01d")){
                        img_weather.setImageResource(R.drawable.clear_day);
                    }else if(weatherList.get(3).getIcon().equals("01n")){
                        img_weather.setImageResource(R.drawable.clear_night);
                    }else if(weatherList.get(3).getIcon().equals("02d")){
                        img_weather.setImageResource(R.drawable.partly_cloudy);
                    }else if(weatherList.get(3).getIcon().equals("02n")){
                        img_weather.setImageResource(R.drawable.cloudy_night);
                    }else if(weatherList.get(3).getIcon().equals("03d") || weatherList.get(3).getIcon().equals("03n") ){
                        img_weather.setImageResource(R.drawable.cloudy);
                    }else if(weatherList.get(3).getIcon().equals("04d") || weatherList.get(3).getIcon().equals("04n") ){
                        img_weather.setImageResource(R.drawable.broken_cloud);
                    }else if(weatherList.get(3).getIcon().equals("09d") || weatherList.get(3).getIcon().equals("09n") ){
                        img_weather.setImageResource(R.drawable.rain);
                    }else if(weatherList.get(3).getIcon().equals("10d") || weatherList.get(3).getIcon().equals("10n") ){
                        img_weather.setImageResource(R.drawable.rain);
                    }else if(weatherList.get(3).getIcon().equals("11d") || weatherList.get(3).getIcon().equals("11n") ){
                        img_weather.setImageResource(R.drawable.broken_cloud);
                    }else if(weatherList.get(3).getIcon().equals("13d") || weatherList.get(3).getIcon().equals("13n") ){
                        img_weather.setImageResource(R.drawable.snow);
                    }else if(weatherList.get(3).getIcon().equals("50d") || weatherList.get(3).getIcon().equals("50n") ){
                        img_weather.setImageResource(R.drawable.mist);
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<SOAnswersResponse> call, Throwable t) {
                Log.d("xxx", t.toString());
            }
        });


    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Weather Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
