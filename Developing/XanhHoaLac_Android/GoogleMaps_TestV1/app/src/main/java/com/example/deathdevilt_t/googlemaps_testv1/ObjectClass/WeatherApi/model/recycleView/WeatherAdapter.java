package com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.WeatherApi.model.recycleView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.WeatherApi.model.List1;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.WeatherApi.model.Main;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.WeatherApi.model.Weather;
import com.example.deathdevilt_t.googlemaps_testv1.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by chtnnnmtgkyp on 5/24/2017.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.MyViewHolder> {

    private List<Main> mainList;
    private List<Weather> weathersList;
    private List<List1> timedailyList;
    private Context context;




    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView temp_max;
        public ImageView img_icon;
        public TextView time_daily;

        public MyViewHolder(View itemView) {
            super(itemView);
            temp_max = (TextView) itemView.findViewById(R.id.temp_max);
            img_icon = (ImageView) itemView.findViewById(R.id.img_icon);
            time_daily = (TextView) itemView.findViewById(R.id.txt_time);
        }
    }

    public WeatherAdapter(List<Main> mainList,List<Weather> weatherList,List<List1> timedailyList,Context context){
        this.mainList = mainList;
        this.weathersList = weatherList;
        this.timedailyList = timedailyList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weather_daily, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Main main = mainList.get(position);
        Weather weather = weathersList.get(position);
        List1 list1 = timedailyList.get(position);
        holder.temp_max.setText(main.getTempMax()+"°C");
        holder.time_daily.setText(list1.getDtTxt()+"");
        Picasso.with(context).load("http://openweathermap.org/img/w/" + weather.getIcon() + ".png").into(holder.img_icon);
    }

    @Override
    public int getItemCount() {
        return weathersList.size();
    }




}
